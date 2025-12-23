package com.thejan.workforceschedule.features.employees.data.models

data class Employee(
    val id: String,
    val firstName: String,
    val lastName: String,
    val employmentType: String,
    val skills: List<String>,
    val dailyHourLimit: Int,
    val weeklyHourLimit: Int,
    val availability: List<EmployeeAvailability>
)

data class EmployeeAvailability(
    val day: String,
    val startTime: String,
    val endTime: String
)