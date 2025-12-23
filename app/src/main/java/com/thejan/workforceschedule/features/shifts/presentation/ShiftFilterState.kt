package com.thejan.workforceschedule.features.shifts.presentation

import androidx.annotation.StringRes
import com.thejan.workforceschedule.R
import com.thejan.workforceschedule.features.shifts.data.models.ShiftStatus
import java.time.LocalDate

data class ShiftFilterState(
    val startDate: LocalDate? = null,
    val endDate: LocalDate? = null,
    val selectedLocation: String? = null,
    val selectedStatus: ShiftStatus? = null
)