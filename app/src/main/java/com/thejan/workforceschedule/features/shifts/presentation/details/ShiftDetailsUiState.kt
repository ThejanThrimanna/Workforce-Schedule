package com.thejan.workforceschedule.features.shifts.presentation.details

import com.thejan.workforceschedule.core.data.local.database.entities.EmployeeEntity
import com.thejan.workforceschedule.core.data.local.database.entities.ShiftEntity

data class ShiftDetailsUiState(
    val shift: ShiftEntity? = null,
    val assignedEmployees: List<EmployeeEntity> = emptyList(),
    val availableEmployees: List<EmployeeEntity> = emptyList(),
    val isLoading: Boolean = false
)