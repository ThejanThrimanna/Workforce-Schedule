package com.thejan.workforceschedule.di

import com.thejan.workforceschedule.features.employees.data.repository.EmployeeRepositoryImpl
import com.thejan.workforceschedule.features.employees.domain.repository.EmployeeRepository
import com.thejan.workforceschedule.features.shifts.data.repository.ShiftRepositoryImpl
import com.thejan.workforceschedule.features.shifts.domain.repository.ShiftRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindShiftRepository(
        impl: ShiftRepositoryImpl,
    ): ShiftRepository

    @Binds
    @Singleton
    abstract fun bindEmployeeRepository(
        impl: EmployeeRepositoryImpl,
    ): EmployeeRepository
}
