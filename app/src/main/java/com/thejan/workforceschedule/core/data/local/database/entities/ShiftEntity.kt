package com.thejan.workforceschedule.core.data.local.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shifts")
data class ShiftEntity(
    @PrimaryKey val id: String,
    val date: String,
    val startTimeMillis: Long,
    val endTimeMillis: Long,
    val location: String,
    val requiredSkills: String,
    val minStaff: Int,
    val assignedEmployeeIds: String,
    val status: String,
    val updatedAt: Long
)