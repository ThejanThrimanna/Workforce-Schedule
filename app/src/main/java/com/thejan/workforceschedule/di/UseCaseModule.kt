package com.thejan.workforceschedule.di

import com.thejan.workforceschedule.features.shifts.domain.repository.ShiftRepository
import com.thejan.workforceschedule.features.shifts.domain.usecase.FetchShiftsUseCase
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
}