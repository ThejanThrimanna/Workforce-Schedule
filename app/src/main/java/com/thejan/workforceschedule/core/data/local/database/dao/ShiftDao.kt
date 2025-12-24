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

    @Query("SELECT * FROM shifts WHERE id = :shiftId LIMIT 1")
    fun observeShiftById(shiftId: String): Flow<ShiftEntity?>

    @Query(
        """
        SELECT EXISTS(
            SELECT 1 FROM shifts
            WHERE assignedEmployeeIds LIKE '%' || :employeeId || '%'
            AND startTimeMillis < :shiftEnd
            AND endTimeMillis > :shiftStart
        )
        """
    )
    suspend fun hasShiftConflict(
        employeeId: String,
        shiftStart: Long,
        shiftEnd: Long
    ): Boolean

    @Query("SELECT assignedEmployeeIds FROM shifts WHERE id = :shiftId LIMIT 1")
    suspend fun getAssignedEmployeeIds(shiftId: String): String?

    @Query(
        """
        UPDATE shifts
        SET assignedEmployeeIds = :employeeIds,
            updatedAt = :updatedAt
        WHERE id = :shiftId
        """
    )
    suspend fun updateAssignedEmployees(
        shiftId: String,
        employeeIds: String,
        updatedAt: Long
    )

}