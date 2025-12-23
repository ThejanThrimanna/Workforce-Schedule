package com.thejan.workforceschedule.features.shifts.data.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.thejan.workforceschedule.core.data.local.database.entities.ShiftEntity
import com.thejan.workforceschedule.features.shifts.data.models.Shift
import com.thejan.workforceschedule.features.shifts.data.models.ShiftStatus
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZoneOffset
import javax.inject.Inject

class ShiftMapper @Inject constructor(
    private val gson: Gson
) {

    @RequiresApi(Build.VERSION_CODES.O)
    fun dtoToEntity(dto: Shift): ShiftEntity {
        val date = LocalDate.parse(dto.date)
        val startLocalTime = LocalTime.parse(dto.startTime)
        val endLocalTime = LocalTime.parse(dto.endTime)

        val startDateTime = LocalDateTime.of(date, startLocalTime)
        val endDateTime = LocalDateTime.of(date, endLocalTime)
        return ShiftEntity(
            id = dto.id,
            date = date.toString(),
            startTimeMillis = startDateTime
                .atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli(),
            endTimeMillis = endDateTime
                .atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli(),
            location = dto.location,
            requiredSkills = gson.toJson(dto.requiredSkills),
            minStaff = dto.minStaff,
            assignedEmployeeIds = gson.toJson(dto.assignedEmployeeIds),
            status = dto.status,
            updatedAt = System.currentTimeMillis()
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun entityToDomain(entity: ShiftEntity): Shift {
        val start = Instant.ofEpochMilli(entity.startTimeMillis)
            .atZone(ZoneId.systemDefault())

        val end = Instant.ofEpochMilli(entity.endTimeMillis)
            .atZone(ZoneId.systemDefault())

        return Shift(
            id = entity.id,
            date = start.toLocalDate().toString(),
            startTime = start.toLocalTime().toString(),
            endTime = end.toLocalTime().toString(),
            location = entity.location,
            requiredSkills = gson.fromJson(
                entity.requiredSkills,
                object : TypeToken<List<String>>() {}.type
            ),
            minStaff = entity.minStaff,
            assignedEmployeeIds = gson.fromJson(
                entity.assignedEmployeeIds,
                object : TypeToken<List<String>>() {}.type
            ),
            status = ShiftStatus.valueOf(entity.status).toString()
        )
    }
}