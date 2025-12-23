package com.thejan.workforceschedule.core.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.thejan.workforceschedule.core.data.local.database.entities.EmployeeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EmployeeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(employees: List<EmployeeEntity>): List<Long>

    @Query("SELECT * FROM employees ORDER BY firstName ASC")
    fun getEmployees(): Flow<List<EmployeeEntity>>

    @Query("DELETE FROM employees")
    suspend fun clearAll()
}