package com.thejan.workforceschedule.features.shifts.presentation.list

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thejan.workforceschedule.features.employees.domain.usecase.FetchEmployeeUseCase
import com.thejan.workforceschedule.features.shifts.domain.usecase.FetchShiftsUseCase
import com.thejan.workforceschedule.features.shifts.domain.usecase.GetShiftsUseCase
import com.thejan.workforceschedule.utils.DateUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class ShiftsViewModel @Inject constructor(
    private val fetchEmployeesUseCase: FetchEmployeeUseCase,
    private val fetchShiftUseCase: FetchShiftsUseCase,
    private val getShiftUseCase: GetShiftsUseCase
) : ViewModel() {

    private val _filterState = MutableStateFlow(initialFilterState())
    val filterState: StateFlow<ShiftFilterState> = _filterState.asStateFlow()

    private val _uiState = MutableStateFlow(
        ShiftsUiState(
            filterState = initialFilterState(),
            shifts = emptyList(),
            isLoading = false
        )
    )
    val uiState: StateFlow<ShiftsUiState> = _uiState

    init {
        fetchEmployees()
        fetchShifts()
        observeShifts()
    }

    private fun fetchShifts() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            fetchShiftUseCase()
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun observeShifts() {
        viewModelScope.launch {
            filterState
                .flatMapLatest { filter ->
                    getShiftUseCase(
                        startDate = filter.startDate,
                        endDate = filter.endDate
                    )
                }
                .collect { shifts ->
                    _uiState.update {
                        it.copy(
                            shifts = shifts,
                            filterState = _filterState.value,
                            isLoading = false
                        )
                    }
                }
        }
    }

    fun onStartDateSelected(date: LocalDate) {
        _filterState.update { current ->
            val newEndDate =
                if (date.isAfter(current.endDate)) date else current.endDate

            current.copy(
                startDate = date,
                endDate = newEndDate
            )
        }
    }

    fun onEndDateSelected(date: LocalDate) {
        _filterState.update { current ->
            if (date.isBefore(current.startDate)) current
            else current.copy(endDate = date)
        }
    }

    private fun fetchEmployees() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            fetchEmployeesUseCase()
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    companion object {
        private fun initialFilterState(): ShiftFilterState {
            return ShiftFilterState(
                startDate = DateUtils.currentWeekStart(),
                endDate = DateUtils.currentWeekEnd()
            )
        }
    }
}