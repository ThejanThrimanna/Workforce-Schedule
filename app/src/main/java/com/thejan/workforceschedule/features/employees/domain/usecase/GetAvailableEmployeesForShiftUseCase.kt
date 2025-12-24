package com.thejan.workforceschedule.features.employees.domain.usecase

import com.thejan.workforceschedule.core.data.local.database.entities.EmployeeEntity
import com.thejan.workforceschedule.features.employees.domain.repository.EmployeeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAvailableEmployeesForShiftUseCase @Inject constructor(
    private val employeeRepository: EmployeeRepository
) {
    suspend operator fun invoke(
        shiftStart: Long,
        shiftEnd: Long,
        requiredSkills: List<String>
    ): Flow<List<EmployeeEntity>> {
        return employeeRepository.getAvailableEmployees(
            shiftStart = shiftStart,
            shiftEnd = shiftEnd,
            requiredSkills = requiredSkills
        )
    }
}