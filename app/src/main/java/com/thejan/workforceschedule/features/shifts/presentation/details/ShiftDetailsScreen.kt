package com.thejan.workforceschedule.features.shifts.presentation.details

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.thejan.workforceschedule.core.data.local.database.entities.EmployeeEntity
import com.thejan.workforceschedule.ui.common.ScreenHeader
import com.thejan.workforceschedule.utils.DateUtils
import com.thejan.workforceschedule.utils.SkillUtils

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ShiftDetailsScreen(
    shiftId: String,
    viewModel: ShiftDetailsViewModel,
    navController: NavController
) {
    val uiState by viewModel.uiState.collectAsState()

    var showAssignSheet by remember { mutableStateOf(false) }

    LaunchedEffect(shiftId) {
        viewModel.loadShift(shiftId)
    }

    if (showAssignSheet) {
        AssignEmployeeBottomSheet(
            employees = uiState.availableEmployees,
            onEmployeeClick = {
                viewModel.assignEmployees(shiftId, listOf(it.id))
                showAssignSheet = false
            },
            onDismiss = { showAssignSheet = false }
        )
    }

    Scaffold(
        topBar = {
            ScreenHeader("Shift Details", true, { navController.popBackStack() })
        }
    ) { padding ->

        uiState.shift?.let { shift ->

            val isStaffIncomplete =
                uiState.assignedEmployees.size < shift.minStaff

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                item { HorizontalDivider() }

                item {
                    Text(DateUtils.millisToDate(shift.startTimeMillis))
                    Text(
                        "${DateUtils.millisToTime(shift.startTimeMillis)} - ${
                            DateUtils.millisToTime(shift.endTimeMillis)
                        }"
                    )
                }

                item {
                    Text("Location", style = MaterialTheme.typography.titleMedium)
                    Text(shift.location)
                }

                item {
                    Text("Required Staff", style = MaterialTheme.typography.titleMedium)
                    Text("${uiState.assignedEmployees.size} / ${shift.minStaff}")
                }

                item {
                    Text("Status", style = MaterialTheme.typography.titleMedium)
                    Text(shift.status)
                }

                item {
                    Text("Skills", style = MaterialTheme.typography.titleMedium)
                    Text(SkillUtils.parseSkills(shift.requiredSkills).joinToString(", "))
                }

                item {
                    Text("Assigned Employees", style = MaterialTheme.typography.titleMedium)
                }

                if (uiState.assignedEmployees.isEmpty()) {
                    item { Text("No employees assigned") }
                } else {
                    items(uiState.assignedEmployees.size) { index ->
                        val employee = uiState.assignedEmployees[index]
                        EmployeeRow(
                            name = "${employee.firstName} ${employee.lastName}",
                            type = employee.employmentType
                        )
                    }
                }

                if (isStaffIncomplete) {
                    item {
                        Button(
                            onClick = {
                                viewModel.loadAvailableEmployees()
                                showAssignSheet = true
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Assign Employee")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EmployeeRow(
    name: String,
    type: String
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = name, style = MaterialTheme.typography.bodyLarge)
            Text(text = type, style = MaterialTheme.typography.bodySmall)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssignEmployeeBottomSheet(
    employees: List<EmployeeEntity>,
    onEmployeeClick: (EmployeeEntity) -> Unit,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss
    ) {
        Text(
            text = "Available Employees",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(16.dp)
        )

        if (employees.isEmpty()) {
            Text(
                text = "No available employees",
                modifier = Modifier.padding(16.dp)
            )
        } else {
            LazyColumn {
                items(employees.size) { index ->
                    val employee = employees[index]

                    ListItem(
                        headlineContent = {
                            Text("${employee.firstName} ${employee.lastName}")
                        },
                        supportingContent = {
                            Text(employee.employmentType)
                        },
                        modifier = Modifier.clickable {
                            onEmployeeClick(employee)
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}