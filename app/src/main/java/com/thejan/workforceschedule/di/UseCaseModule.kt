package com.thejan.workforceschedule.di

import com.thejan.workforceschedule.features.employees.domain.repository.EmployeeRepository
import com.thejan.workforceschedule.features.employees.domain.usecase.FetchEmployeeUseCase
import com.thejan.workforceschedule.features.employees.domain.usecase.GetAvailableEmployeesForShiftUseCase
import com.thejan.workforceschedule.features.employees.domain.usecase.GetEmployeeUseCase
import com.thejan.workforceschedule.features.shifts.domain.repository.ShiftRepository
import com.thejan.workforceschedule.features.shifts.domain.usecase.AssignEmployeesToShiftUseCase
import com.thejan.workforceschedule.features.shifts.domain.usecase.FetchShiftsUseCase
import com.thejan.workforceschedule.features.shifts.domain.usecase.GetShiftByIdUseCase
import com.thejan.workforceschedule.features.shifts.domain.usecase.GetShiftsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    @Singleton
    fun provideFetchShiftUseCase(
        repository: ShiftRepository,
    ): FetchShiftsUseCase {
        return FetchShiftsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetShiftUseCase(
        repository: ShiftRepository,
    ): GetShiftsUseCase {
        return GetShiftsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideFetchEmployeeUseCase(
        repository: EmployeeRepository,
    ): FetchEmployeeUseCase {
        return FetchEmployeeUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetEmployeeUseCase(
        repository: EmployeeRepository,
    ): GetEmployeeUseCase {
        return GetEmployeeUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetShiftByIdUseCase(
        repository: ShiftRepository,
    ): GetShiftByIdUseCase {
        return GetShiftByIdUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideAvailableEmployeesUseCase(
        repository: EmployeeRepository,
    ): GetAvailableEmployeesForShiftUseCase {
        return GetAvailableEmployeesForShiftUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideAssignEmployeesUseCase(
        repository: ShiftRepository,
    ): AssignEmployeesToShiftUseCase {
        return AssignEmployeesToShiftUseCase(repository)
    }
}