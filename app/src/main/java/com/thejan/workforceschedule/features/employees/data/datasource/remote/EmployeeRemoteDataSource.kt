package com.thejan.workforceschedule.features.employees.data.datasource.remote

import com.thejan.workforceschedule.features.employees.data.models.Employee

interface EmployeeRemoteDataSource {
    suspend fun fetchEmployees(): List<Employee>
}