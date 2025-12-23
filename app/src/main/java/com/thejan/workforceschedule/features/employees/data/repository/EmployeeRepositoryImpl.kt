package com.thejan.workforceschedule.features.employees.data.repository

import com.thejan.workforceschedule.core.data.local.database.dao.EmployeeDao
import com.thejan.workforceschedule.core.data.local.database.entities.EmployeeEntity
import com.thejan.workforceschedule.features.employees.data.datasource.remote.MockEmployeeRemoteDataSource
import com.thejan.workforceschedule.features.employees.data.mapper.EmployeeMapper
import com.thejan.workforceschedule.features.employees.domain.repository.EmployeeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EmployeeRepositoryImpl @Inject constructor(
    private val dao: EmployeeDao,
    private val localJson: MockEmployeeRemoteDataSource,
    private val mapper: EmployeeMapper
) : EmployeeRepository {

    override suspend fun getEmployees(): Flow<List<EmployeeEntity>> {
        return dao.getEmployees()
    }

    override suspend fun fetchEmployees(): Boolean {
        val employees = localJson.fetchEmployees()
        val entities = employees.map { employee ->
            mapper.run { employee.toEntity() }
        }
        return dao.upsertAll(entities).isEmpty()
    }
}