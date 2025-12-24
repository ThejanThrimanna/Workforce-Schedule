package com.thejan.workforceschedule.features.shifts.presentation.list

import com.thejan.workforceschedule.core.data.local.database.entities.ShiftEntity

data class ShiftsUiState(
    val isLoading: Boolean = false,
    val shifts: List<ShiftEntity> = emptyList(),
    val filterState: ShiftFilterState? = null,
    val errorMessage: String? = null,
)