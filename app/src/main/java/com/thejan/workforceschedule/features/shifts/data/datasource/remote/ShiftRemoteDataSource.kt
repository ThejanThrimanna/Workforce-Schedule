package com.thejan.workforceschedule.features.shifts.data.datasource.remote

import com.thejan.workforceschedule.features.shifts.data.models.Shift

interface ShiftRemoteDataSource {
    suspend fun fetchShifts(): List<Shift>
}