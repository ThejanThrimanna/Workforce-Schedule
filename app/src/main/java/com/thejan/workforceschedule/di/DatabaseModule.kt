package com.thejan.workforceschedule.di

import android.content.Context
import androidx.room.Room
import com.thejan.workforceschedule.core.data.local.database.AppDatabase
import com.thejan.workforceschedule.core.data.local.database.dao.EmployeeDao
import com.thejan.workforceschedule.core.data.local.database.dao.ShiftDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "workforce_db"
        ).build()

    @Provides
    fun provideShiftDao(
        database: AppDatabase
    ): ShiftDao = database.shiftDao()

    @Provides
    fun provideEmployeeDao(
        database: AppDatabase
    ): EmployeeDao = database.employeeDao()
}
