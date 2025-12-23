package com.thejan.workforceschedule.features.shifts.data.models

data class Shift(
    val id: String,
    val date: String,
    val startTime: String,
    val endTime: String,
    val location: String,
    val requiredSkills: List<String>,
    val minStaff: Int,
    val assignedEmployeeIds: List<String>,
    val status: String
)