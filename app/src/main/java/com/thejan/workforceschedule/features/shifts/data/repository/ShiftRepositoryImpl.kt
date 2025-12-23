package com.thejan.workforceschedule.features.shifts.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.thejan.workforceschedule.core.data.local.database.dao.ShiftDao
import com.thejan.workforceschedule.core.data.local.database.entities.ShiftEntity
import com.thejan.workforceschedule.features.shifts.data.datasource.remote.MockShiftRemoteDataSource
import com.thejan.workforceschedule.features.shifts.data.mapper.ShiftMapper
import com.thejan.workforceschedule.features.shifts.domain.repository.ShiftRepository
import com.thejan.workforceschedule.utils.DateUtils
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class ShiftRepositoryImpl @Inject constructor(
    private val dao: ShiftDao,
    private val localJson: MockShiftRemoteDataSource,
    private val mapper: ShiftMapper
) : ShiftRepository {

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getShifts(
        startDate: LocalDate?,
        endDate: LocalDate?
    ): Flow<List<ShiftEntity>> {
        val startDateMilliseconds = DateUtils.localDateToMillis(startDate)
        val endDateMilliseconds = DateUtils.localDateToMillis(endDate)
        return dao.observeShifts(startDateMilliseconds, endDateMilliseconds)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun fetchShifts(): Boolean {
        val shifts = localJson.fetchShifts()
        val entities = shifts.map(mapper::dtoToEntity)
        return dao.upsertAll(entities).isEmpty()
    }
}