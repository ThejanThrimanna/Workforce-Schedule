package com.thejan.workforceschedule.features.employees.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.thejan.workforceschedule.core.data.local.database.entities.EmployeeEntity
import com.thejan.workforceschedule.ui.common.ScreenHeader

@Composable
fun EmployeesScreen(viewModel: EmployeesViewModel) {

    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            EmployeesTopBar()
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            HorizontalDivider()

            EmployeeListContent(
                employees = uiState.employees,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmployeesTopBar() {
    ScreenHeader("Employees")
}

@Composable
fun EmployeeListContent(
    employees: List<EmployeeEntity>,
    modifier: Modifier = Modifier
) {
    if (employees.isEmpty()) {
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No employees available",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    } else {
        LazyColumn(
            modifier = modifier,
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(employees.size) { index ->
                EmployeeListItem(employees[index])
            }
        }
    }
}

@Composable
fun EmployeeListItem(employee: EmployeeEntity) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {

            Text(
                text = "${employee.firstName}  ${employee.lastName}",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = employee.employmentType,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

