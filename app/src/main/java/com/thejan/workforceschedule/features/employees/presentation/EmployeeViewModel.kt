package com.thejan.workforceschedule.features.employees.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thejan.workforceschedule.features.employees.domain.usecase.FetchEmployeeUseCase
import com.thejan.workforceschedule.features.employees.domain.usecase.GetEmployeeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmployeesViewModel @Inject constructor(
    private val getEmployeesUseCase: GetEmployeeUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        EmployeesUiState(
            employees = emptyList(),
            isLoading = false
        )
    )
    val uiState: StateFlow<EmployeesUiState> = _uiState.asStateFlow()

    init {
        observeEmployees()
    }

    private fun observeEmployees() {
        viewModelScope.launch {
            getEmployeesUseCase()
                .collect { employees ->
                    _uiState.update {
                        it.copy(employees = employees)
                    }
                }
        }
    }
}