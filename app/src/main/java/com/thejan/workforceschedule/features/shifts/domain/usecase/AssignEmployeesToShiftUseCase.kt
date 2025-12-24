package com.thejan.workforceschedule.features.shifts.domain.usecase

import com.thejan.workforceschedule.features.shifts.domain.repository.ShiftRepository
import javax.inject.Inject

class AssignEmployeesToShiftUseCase @Inject constructor(
    private val repository: ShiftRepository
) {

    suspend operator fun invoke(
        shiftId: String,
        employeeIds: List<String>
    ) {
        repository.updateAssignedEmployees(
            shiftId = shiftId,
            employeeIds = employeeIds
        )
    }
}