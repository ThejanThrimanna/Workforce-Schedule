package com.thejan.workforceschedule.features.employees.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.thejan.workforceschedule.core.data.local.database.dao.EmployeeDao
import com.thejan.workforceschedule.core.data.local.database.dao.ShiftDao
import com.thejan.workforceschedule.core.data.local.database.entities.EmployeeEntity
import com.thejan.workforceschedule.features.employees.data.datasource.remote.MockEmployeeRemoteDataSource
import com.thejan.workforceschedule.features.employees.data.mapper.EmployeeMapper
import com.thejan.workforceschedule.features.employees.domain.repository.EmployeeRepository
import com.thejan.workforceschedule.utils.AvailabilityUtils
import com.thejan.workforceschedule.utils.SkillUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.Instant
import java.time.LocalTime
import java.time.ZoneId
import javax.inject.Inject

class EmployeeRepositoryImpl @Inject constructor(
    private val dao: EmployeeDao,
    private val localJson: MockEmployeeRemoteDataSource,
    private val mapper: EmployeeMapper,
    private val shiftDao: ShiftDao
) : EmployeeRepository {

    override suspend fun getEmployees(): Flow<List<EmployeeEntity>> {
        return dao.getEmployees()
    }

    override suspend fun fetchEmployees(): Boolean {
        val employees = localJson.fetchEmployees()
        val entities = employees.map { employee ->
            mapper.run { employee.toEntity() }
        }
        return dao.upsertAll(entities).isEmpty()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getAvailableEmployees(
        shiftStart: Long,
        shiftEnd: Long,
        requiredSkills: List<String>
    ): Flow<List<EmployeeEntity>> {

        return dao.getEmployees().map { employees ->
            employees.filter { employee ->

                val employeeSkills = SkillUtils.parseSkills(employee.skillsJson)
                if (!hasRequiredSkills(employeeSkills, requiredSkills)) {
                    return@filter false
                }

                if (
                    !isEmployeeAvailableInTimeWindow(
                        employee.availabilityJson,
                        shiftStart,
                        shiftEnd
                    )
                ) {
                    return@filter false
                }

                val hasConflict = shiftDao.hasShiftConflict(
                    employeeId = employee.id,
                    shiftStart = shiftStart,
                    shiftEnd = shiftEnd
                )

                !hasConflict
            }
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun isEmployeeAvailableInTimeWindow(
        availabilityJson: String,
        shiftStartMillis: Long,
        shiftEndMillis: Long
    ): Boolean {

        val availabilityList = AvailabilityUtils.parseAvailability(availabilityJson)
        if (availabilityList.isEmpty()) return false

        val zoneId = ZoneId.systemDefault()

        val shiftStartDateTime =
            Instant.ofEpochMilli(shiftStartMillis).atZone(zoneId)
        val shiftEndDateTime =
            Instant.ofEpochMilli(shiftEndMillis).atZone(zoneId)

        val shiftDay = shiftStartDateTime.dayOfWeek.name
        val shiftStartTime = shiftStartDateTime.toLocalTime()
        val shiftEndTime = shiftEndDateTime.toLocalTime()

        val dayAvailability = availabilityList.firstOrNull {
            it.day == shiftDay
        } ?: return false

        val availableStart = LocalTime.parse(dayAvailability.startTime)
        val availableEnd = LocalTime.parse(dayAvailability.endTime)

        return !shiftStartTime.isBefore(availableStart) &&
                !shiftEndTime.isAfter(availableEnd)
    }

    fun hasRequiredSkills(
        employeeSkills: List<String>,
        requiredSkills: List<String>
    ): Boolean {
        return requiredSkills.all { it in employeeSkills }
    }
}