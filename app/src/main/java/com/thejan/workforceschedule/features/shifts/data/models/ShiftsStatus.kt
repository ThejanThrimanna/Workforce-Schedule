package com.thejan.workforceschedule.features.shifts.data.models

import androidx.annotation.StringRes
import com.thejan.workforceschedule.R

enum class ShiftStatus {
    OPEN,
    PARTIALLY_FILLED,
    FILLED
}

@StringRes
fun ShiftStatus.labelRes(): Int = when (this) {
    ShiftStatus.OPEN -> R.string.shift_status_open
    ShiftStatus.PARTIALLY_FILLED -> R.string.shift_status_partially_filled
    ShiftStatus.FILLED -> R.string.shift_status_filled
}