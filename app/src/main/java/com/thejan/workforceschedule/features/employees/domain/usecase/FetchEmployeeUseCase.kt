package com.thejan.workforceschedule.features.employees.domain.usecase

import com.thejan.workforceschedule.features.employees.domain.repository.EmployeeRepository
import javax.inject.Inject

class FetchEmployeeUseCase @Inject constructor(
    private val repository: EmployeeRepository,
) {
    suspend operator fun invoke(): Boolean = repository.fetchEmployees()
}
