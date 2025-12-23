package com.thejan.workforceschedule.features.shifts.domain.usecase

import com.thejan.workforceschedule.core.data.local.database.entities.ShiftEntity
import com.thejan.workforceschedule.features.shifts.domain.repository.ShiftRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class GetShiftsUseCase @Inject constructor(
    private val repository: ShiftRepository,
) {
    suspend operator fun invoke(
        startDate: LocalDate?,
        endDate: LocalDate?
    ): Flow<List<ShiftEntity>> =
        repository.getShifts(startDate, endDate)
}