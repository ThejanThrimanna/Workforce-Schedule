package com.thejan.workforceschedule.features.shifts.domain.repository

import com.thejan.workforceschedule.core.data.local.database.entities.ShiftEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface ShiftRepository {
    suspend fun getShifts(startDate: LocalDate?, endDate: LocalDate?): Flow<List<ShiftEntity>>
    suspend fun fetchShifts()
}