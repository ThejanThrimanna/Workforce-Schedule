package com.thejan.workforceschedule.navigation

sealed class Screen(val route: String) {
    object Dashboard : Screen("dashboard")
    object Shifts : Screen("shift")
    object ShiftDetails : Screen("shift_details/{shiftId}") {
        fun createRoute(shiftId: String) = "shift_details/$shiftId"
    }

    object Employee : Screen("employee")
    object EmployeeDetails : Screen("employeeDetails")
}