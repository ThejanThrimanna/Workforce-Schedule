package com.thejan.workforceschedule.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.thejan.workforceschedule.features.dashboard.presentation.DashboardScreen
import com.thejan.workforceschedule.features.shifts.presentation.details.ShiftDetailsScreen
import com.thejan.workforceschedule.features.shifts.presentation.details.ShiftDetailsViewModel
import com.thejan.workforceschedule.features.shifts.presentation.list.ShiftsScreen
import com.thejan.workforceschedule.features.shifts.presentation.list.ShiftsViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Dashboard.route
    ) {
        composable(Screen.Dashboard.route) {
            DashboardScreen(navController)
        }

        composable(Screen.Shifts.route) {
            val viewModel: ShiftsViewModel = hiltViewModel()
            ShiftsScreen(
                viewModel = viewModel,
                navController = navController
            )
        }

        composable(
            route = Screen.ShiftDetails.route,
            arguments = listOf(
                navArgument("shiftId") { type = NavType.StringType }
            )
        ) { backStackEntry ->

            val viewModel: ShiftDetailsViewModel = hiltViewModel()

            ShiftDetailsScreen(
                shiftId = backStackEntry.arguments!!.getString("shiftId")!!,
                viewModel = viewModel,
                navController = navController
            )
        }
    }
}