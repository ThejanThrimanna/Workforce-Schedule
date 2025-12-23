package com.thejan.workforceschedule.core.data.local.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "employees")
data class EmployeeEntity(
    @PrimaryKey
    val id: String,
    val firstName: String,
    val lastName: String,
    val employmentType: String,
    val skillsJson: String,
    val dailyHourLimit: Int,
    val weeklyHourLimit: Int,
    val availabilityJson: String,
    val updatedAt: Long
)