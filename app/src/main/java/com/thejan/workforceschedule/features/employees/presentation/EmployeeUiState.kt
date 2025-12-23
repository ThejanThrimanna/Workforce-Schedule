package com.thejan.workforceschedule.features.employees.presentation

import com.thejan.workforceschedule.core.data.local.database.entities.EmployeeEntity

data class EmployeesUiState(
    val employees: List<EmployeeEntity>,
    val isLoading: Boolean
)