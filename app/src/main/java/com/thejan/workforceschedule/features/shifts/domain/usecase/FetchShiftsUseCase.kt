package com.thejan.workforceschedule.features.shifts.domain.usecase

import com.thejan.workforceschedule.features.shifts.domain.repository.ShiftRepository
import javax.inject.Inject


class FetchShiftsUseCase @Inject constructor(
    private val repository: ShiftRepository,
) {
    suspend operator fun invoke(): Boolean = repository.fetchShifts()
}
