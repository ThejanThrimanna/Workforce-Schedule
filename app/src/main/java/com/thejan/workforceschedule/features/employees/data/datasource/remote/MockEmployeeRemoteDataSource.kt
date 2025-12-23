package com.thejan.workforceschedule.features.employees.data.datasource.remote

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.thejan.workforceschedule.core.data.local.mock.AssetJsonReader
import com.thejan.workforceschedule.features.employees.data.models.Employee
import javax.inject.Inject

class MockEmployeeRemoteDataSource @Inject constructor(
    private val assetJsonReader: AssetJsonReader,
    private val gson: Gson
) : EmployeeRemoteDataSource {

    override suspend fun fetchEmployees(): List<Employee> {
        val json = assetJsonReader.read("employees.json")
        return gson.fromJson(
            json,
            object : TypeToken<List<Employee>>() {}.type
        )
    }
}