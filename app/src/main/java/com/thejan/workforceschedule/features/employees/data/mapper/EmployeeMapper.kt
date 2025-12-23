package com.thejan.workforceschedule.features.employees.data.mapper

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.thejan.workforceschedule.core.data.local.database.entities.EmployeeEntity
import com.thejan.workforceschedule.features.employees.data.models.Employee
import com.thejan.workforceschedule.features.employees.data.models.EmployeeAvailability
import javax.inject.Inject

class EmployeeMapper @Inject constructor(
    private val gson: Gson
) {

    fun Employee.toEntity(): EmployeeEntity {
        return EmployeeEntity(
            id = id,
            firstName = firstName,
            lastName = lastName,
            employmentType = employmentType,
            skillsJson = gson.toJson(skills),
            dailyHourLimit = dailyHourLimit,
            weeklyHourLimit = weeklyHourLimit,
            availabilityJson = gson.toJson(availability),
            updatedAt = System.currentTimeMillis()
        )
    }

    fun EmployeeEntity.toDomain(): Employee {
        return Employee(
            id = id,
            firstName = firstName,
            lastName = lastName,
            employmentType = employmentType,
            skills = gson.fromJson(
                skillsJson,
                object : TypeToken<List<String>>() {}.type
            ),
            dailyHourLimit = dailyHourLimit,
            weeklyHourLimit = weeklyHourLimit,
            availability = gson.fromJson(
                availabilityJson,
                object : TypeToken<List<EmployeeAvailability>>() {}.type
            )
        )
    }
}