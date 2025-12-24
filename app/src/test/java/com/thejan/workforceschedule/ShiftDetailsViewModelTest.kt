package com.thejan.workforceschedule

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.thejan.workforceschedule.core.data.local.database.entities.EmployeeEntity
import com.thejan.workforceschedule.core.data.local.database.entities.ShiftEntity
import com.thejan.workforceschedule.features.employees.domain.usecase.GetAvailableEmployeesForShiftUseCase
import com.thejan.workforceschedule.features.employees.domain.usecase.GetEmployeeUseCase
import com.thejan.workforceschedule.features.shifts.domain.usecase.AssignEmployeesToShiftUseCase
import com.thejan.workforceschedule.features.shifts.domain.usecase.GetShiftByIdUseCase
import com.thejan.workforceschedule.features.shifts.presentation.details.ShiftDetailsViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ShiftDetailsViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val dispatcher = StandardTestDispatcher()

    private val getShiftByIdUseCase: GetShiftByIdUseCase = mockk()
    private val getEmployeeUseCase: GetEmployeeUseCase = mockk()
    private val getAvailableEmployeesForShiftUseCase: GetAvailableEmployeesForShiftUseCase = mockk()
    private val assignEmployeesToShiftUseCase: AssignEmployeesToShiftUseCase = mockk(relaxed = true)

    private lateinit var viewModel: ShiftDetailsViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)

        coEvery { getShiftByIdUseCase(any()) } returns flowOf(mockShift())
        coEvery { getEmployeeUseCase() } returns flowOf(mockEmployees())
        coEvery {
            getAvailableEmployeesForShiftUseCase(any(), any(), any())
        } returns flowOf(mockAvailableEmployees())

        viewModel = ShiftDetailsViewModel(
            getShiftByIdUseCase,
            getEmployeeUseCase,
            getAvailableEmployeesForShiftUseCase,
            assignEmployeesToShiftUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadShift updates uiState with shift and employees`() = runTest {
        viewModel.loadShift("shift_1")
        advanceUntilIdle()

        val state = viewModel.uiState.value

        assertFalse(state.isLoading)
        assertEquals("shift_1", state.shift?.id)
        assertEquals(1, state.assignedEmployees.size)
        assertEquals("emp_1", state.assignedEmployees.first().id)
        assertEquals(2, state.availableEmployees.size)
    }

    @Test
    fun `assignEmployees calls use case`() = runTest {
        viewModel.assignEmployees(
            shiftId = "shift_1",
            employeeIds = listOf("emp_1", "emp_2")
        )
        advanceUntilIdle()

        coVerify {
            assignEmployeesToShiftUseCase(
                shiftId = "shift_1",
                employeeIds = listOf("emp_1", "emp_2")
            )
        }
    }

    @Test
    fun `loadAvailableEmployees updates available employees`() = runTest {
        viewModel.loadShift("shift_1")
        advanceUntilIdle()

        viewModel.loadAvailableEmployees()
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertEquals(2, state.availableEmployees.size)
    }

    private fun mockShift(): ShiftEntity {
        return ShiftEntity(
            id = "shift_1",
            date = "2025-01-01",
            location = "Colombo",
            startTimeMillis = 1_000L,
            endTimeMillis = 5_000L,
            requiredSkills = "[\"Cashier\"]",
            minStaff = 1,
            status = "OPEN",
            assignedEmployeeIds = "emp_1",
            updatedAt = System.currentTimeMillis()
        )
    }

    private fun mockEmployees(): List<EmployeeEntity> {
        return listOf(
            EmployeeEntity(
                id = "emp_1",
                firstName = "John",
                lastName = "Doe",
                employmentType = "FULL_TIME",
                skillsJson = "[\"Cashier\"]",
                dailyHourLimit = 8,
                weeklyHourLimit = 40,
                availabilityJson = "{}",
                updatedAt = System.currentTimeMillis()
            ),
            EmployeeEntity(
                id = "emp_2",
                firstName = "Jane",
                lastName = "Smith",
                employmentType = "PART_TIME",
                skillsJson = "[\"Cashier\"]",
                dailyHourLimit = 6,
                weeklyHourLimit = 30,
                availabilityJson = "{}",
                updatedAt = System.currentTimeMillis()
            )
        )
    }

    private fun mockAvailableEmployees(): List<EmployeeEntity> {
        return mockEmployees()
    }
}