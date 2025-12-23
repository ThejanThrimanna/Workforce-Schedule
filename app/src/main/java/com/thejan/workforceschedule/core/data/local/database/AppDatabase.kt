package com.thejan.workforceschedule.core.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.thejan.workforceschedule.core.data.local.database.dao.ShiftDao
import com.thejan.workforceschedule.core.data.local.database.entities.ShiftEntity

@Database(
    entities = [ShiftEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun shiftDao(): ShiftDao
}