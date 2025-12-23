package com.thejan.workforceschedule.features.dashboard.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.compose.composable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.thejan.workforceschedule.features.dashboard.data.model.BottomNavItem
import com.thejan.workforceschedule.features.employees.presentation.EmployeesScreen
import com.thejan.workforceschedule.features.shifts.presentation.ShiftsScreen
import com.thejan.workforceschedule.navigation.Screen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.thejan.workforceschedule.features.shifts.presentation.ShiftsViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DashboardScreen(rootNavController: NavController) {

    val dashboardNavController = rememberNavController()

    val items = listOf(
        BottomNavItem(
            route = Screen.Shifts.route,
            label = "Shifts",
            icon = Icons.Default.AccountBox
        ),
        BottomNavItem(
            route = Screen.Employee.route,
            label = "Employees",
            icon = Icons.Default.Person
        )
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                val currentRoute =
                    dashboardNavController
                        .currentBackStackEntryAsState()
                        .value?.destination?.route

                items.forEach { item ->
                    NavigationBarItem(
                        selected = currentRoute == item.route,
                        onClick = {
                            dashboardNavController.navigate(item.route) {
                                popUpTo(Screen.Shifts.route)
                                launchSingleTop = true
                            }
                        },
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = { Text(item.label) }
                    )
                }
            }
        }
    ) { padding ->

        NavHost(
            navController = dashboardNavController,
            startDestination = Screen.Shifts.route,
        ) {
            composable(Screen.Shifts.route) { backStackEntry ->
                val viewModel: ShiftsViewModel = hiltViewModel(backStackEntry)
                ShiftsScreen(viewModel)
            }
            composable(Screen.Employee.route) {
                EmployeesScreen()
            }
        }
    }
}