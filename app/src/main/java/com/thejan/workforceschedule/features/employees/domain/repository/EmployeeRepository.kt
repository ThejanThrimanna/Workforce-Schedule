package com.thejan.workforceschedule.features.employees.domain.repository

import com.thejan.workforceschedule.core.data.local.database.entities.EmployeeEntity
import kotlinx.coroutines.flow.Flow

interface EmployeeRepository {
    suspend fun getEmployees(): Flow<List<EmployeeEntity>>
    suspend fun fetchEmployees(): Boolean

    suspend fun getAvailableEmployees(
        shiftStart: Long,
        shiftEnd: Long,
        requiredSkills: List<String>
    ): Flow<List<EmployeeEntity>>
}