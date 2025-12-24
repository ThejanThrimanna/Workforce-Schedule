package com.thejan.workforceschedule.features.shifts.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thejan.workforceschedule.features.employees.domain.usecase.GetAvailableEmployeesForShiftUseCase
import com.thejan.workforceschedule.features.employees.domain.usecase.GetEmployeeUseCase
import com.thejan.workforceschedule.features.shifts.domain.usecase.AssignEmployeesToShiftUseCase
import com.thejan.workforceschedule.features.shifts.domain.usecase.GetShiftByIdUseCase
import com.thejan.workforceschedule.utils.SkillUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShiftDetailsViewModel @Inject constructor(
    private val getShiftByIdUseCase: GetShiftByIdUseCase,
    private val getEmployeeUseCase: GetEmployeeUseCase,
    private val getAvailableEmployeesForShiftUseCase: GetAvailableEmployeesForShiftUseCase,
    private val assignEmployeesToShiftUseCase: AssignEmployeesToShiftUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ShiftDetailsUiState())
    val uiState: StateFlow<ShiftDetailsUiState> = _uiState

    @OptIn(ExperimentalCoroutinesApi::class)
    fun loadShift(shiftId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            getShiftByIdUseCase(shiftId)
                .flatMapLatest { shift ->
                    if (shift == null) {
                        return@flatMapLatest kotlinx.coroutines.flow.flowOf(
                            Triple(null, emptyList(), emptyList())
                        )
                    }

                    combine(
                        getEmployeeUseCase(),
                        getAvailableEmployeesForShiftUseCase(
                            shift.startTimeMillis,
                            shift.endTimeMillis,
                            SkillUtils.parseSkills(shift.requiredSkills)
                        )
                    ) { allEmployees, availableEmployees ->

                        val assignedEmployees = allEmployees.filter {
                            shift.assignedEmployeeIds.contains(it.id)
                        }

                        Triple(shift, assignedEmployees, availableEmployees)
                    }
                }
                .collect { (shift, assigned, available) ->
                    _uiState.update {
                        it.copy(
                            shift = shift,
                            assignedEmployees = assigned,
                            availableEmployees = available,
                            isLoading = false
                        )
                    }
                }
        }
    }

    fun assignEmployees(
        shiftId: String,
        employeeIds: List<String>
    ) {
        viewModelScope.launch {
            assignEmployeesToShiftUseCase(
                shiftId = shiftId,
                employeeIds = employeeIds
            )
        }
    }
    fun loadAvailableEmployees() {
        val shift = _uiState.value.shift ?: return

        viewModelScope.launch {
            getAvailableEmployeesForShiftUseCase(
                shiftStart = shift.startTimeMillis,
                shiftEnd = shift.endTimeMillis,
                SkillUtils.parseSkills(shift.requiredSkills)
            ).collect { employees ->
                _uiState.update {
                    it.copy(availableEmployees = employees)
                }
            }
        }
    }
}