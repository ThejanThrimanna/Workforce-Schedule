package com.thejan.workforceschedule

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.thejan.workforceschedule.core.data.local.database.entities.ShiftEntity
import com.thejan.workforceschedule.features.employees.domain.usecase.FetchEmployeeUseCase
import com.thejan.workforceschedule.features.shifts.domain.usecase.FetchShiftsUseCase
import com.thejan.workforceschedule.features.shifts.domain.usecase.GetShiftsUseCase
import com.thejan.workforceschedule.features.shifts.presentation.list.ShiftsViewModel
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
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
import java.time.LocalDate

@OptIn(ExperimentalCoroutinesApi::class)
@ExperimentalCoroutinesApi
class ShiftsViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    private val fetchShiftsUseCase: FetchShiftsUseCase = mockk(relaxed = true)
    private val fetchEmployeesUseCase: FetchEmployeeUseCase = mockk(relaxed = true)
    private val getShiftsUseCase: GetShiftsUseCase = mockk()

    private lateinit var viewModel: ShiftsViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        coEvery {
            getShiftsUseCase(any(), any())
        } returns flowOf(mockShiftList())

        viewModel = ShiftsViewModel(
            fetchEmployeesUseCase = fetchEmployeesUseCase,
            fetchShiftUseCase = fetchShiftsUseCase,
            getShiftUseCase = getShiftsUseCase,
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `init fetches and observes shifts`() = runTest {
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertEquals(1, state.shifts.size)
        assertEquals("shift_1", state.shifts.first().id)
    }

    @Test
    fun `start date update changes filter state`() = runTest {
        val newDate = LocalDate.of(2025, 1, 10)

        viewModel.onStartDateSelected(newDate)
        advanceUntilIdle()

        val filter = viewModel.filterState.value
        assertEquals(newDate, filter.startDate)
    }

    @Test
    fun `end date before start date is ignored`() = runTest {
        val startDate = LocalDate.of(2025, 1, 10)
        val invalidEndDate = LocalDate.of(2025, 1, 5)

        viewModel.onStartDateSelected(startDate)
        viewModel.onEndDateSelected(invalidEndDate)
        advanceUntilIdle()

        val filter = viewModel.filterState.value
        assertTrue(filter.endDate?.isAfter(filter.startDate) == true || filter.endDate == filter.startDate)
    }

    private fun mockShiftList(): List<ShiftEntity> {
        return listOf(
            ShiftEntity(
                id = "shift_1",
                date = "2025-12-25",
                location = "HQ",
                requiredSkills = "[\"Cashier\"]",
                minStaff = 2,
                status = "OPEN",
                assignedEmployeeIds = "",
                startTimeMillis = 1766629800000,
                endTimeMillis = 1766644200000,
                updatedAt = System.currentTimeMillis()
            )
        )
    }
}
