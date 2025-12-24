package com.thejan.workforceschedule.features.shifts.domain.usecase

import com.thejan.workforceschedule.core.data.local.database.entities.ShiftEntity
import com.thejan.workforceschedule.features.shifts.domain.repository.ShiftRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetShiftByIdUseCase @Inject constructor(
    private val repository: ShiftRepository
) {
    suspend operator fun invoke(shiftId: String): Flow<ShiftEntity?> {
        return repository.getShiftById(shiftId)
    }
}