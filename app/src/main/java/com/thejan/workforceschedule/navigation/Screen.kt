package com.thejan.workforceschedule.navigation

sealed class Screen(val route: String) {
    object Dashboard : Screen("dashboard")
    object Shifts : Screen("shift")
    object ShiftDetails : Screen("shiftDetails")
    object Employee : Screen("employee")
    object EmployeeDetails : Screen("employeeDetails")
}