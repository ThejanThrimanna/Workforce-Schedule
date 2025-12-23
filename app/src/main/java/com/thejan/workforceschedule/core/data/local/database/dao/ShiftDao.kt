package com.thejan.workforceschedule.core.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.thejan.workforceschedule.core.data.local.database.entities.ShiftEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ShiftDao {
    @Query(
        """
        SELECT * FROM shifts
        WHERE startTimeMillis BETWEEN :startDate AND :endDate
        ORDER BY startTimeMillis, startTimeMillis
    """
    )
    fun observeShifts(
        startDate: Long,
        endDate: Long
    ): Flow<List<ShiftEntity>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(shifts: List<ShiftEntity>): List<Long>

    @Query("SELECT COUNT(*) FROM shifts")
    suspend fun count(): Int
}