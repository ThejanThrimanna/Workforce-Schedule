package com.thejan.workforceschedule.features.shifts.data.datasource.remote

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.thejan.workforceschedule.core.data.local.mock.AssetJsonReader
import com.thejan.workforceschedule.features.shifts.data.models.Shift
import javax.inject.Inject

class MockShiftRemoteDataSource @Inject constructor(
    private val assetJsonReader: AssetJsonReader,
    private val gson: Gson
) : ShiftRemoteDataSource {

    override suspend fun fetchShifts(): List<Shift> {
        val json = assetJsonReader.read("shifts.json")
        return gson.fromJson(
            json,
            object : TypeToken<List<Shift>>() {}.type
        )
    }
}