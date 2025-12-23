package com.thejan.workforceschedule.features.shifts.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.thejan.workforceschedule.core.data.local.database.entities.ShiftEntity
import com.thejan.workforceschedule.ui.dialogs.DatePickerDialogWrapper
import com.thejan.workforceschedule.utils.DateUtils
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ShiftsScreen(viewModel: ShiftsViewModel) {

    val uiState by viewModel.uiState.collectAsState()

    var showStartPicker by remember { mutableStateOf(false) }
    var showEndPicker by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            ShiftsTopBar()
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            ShiftsFilterBar(
                filterState = uiState.filterState,
                onStartDateClick = {
                    showStartPicker = true
                },
                onEndDateClick = {
                    showEndPicker = true
                },
                onAdvancedFilterClick = viewModel::onAdvancedFilterClicked
            )

            HorizontalDivider()

            ShiftListContent(
                shifts = uiState.shifts,
                modifier = Modifier.fillMaxSize()
            )
        }
    }

    if (showStartPicker) {
        uiState.filterState?.startDate?.let {
            DatePickerDialogWrapper(
                initialDate = it,
                onDismiss = { showStartPicker = false },
                onDateSelected = {
                    viewModel.onStartDateSelected(it)
                    showStartPicker = false
                }
            )
        }
    }

    if (showEndPicker) {
        uiState.filterState?.endDate?.let {
            DatePickerDialogWrapper(
                initialDate = it,
                minDate = uiState.filterState!!.startDate,
                onDismiss = { showEndPicker = false },
                onDateSelected = {
                    viewModel.onEndDateSelected(it)
                    showEndPicker = false
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShiftsTopBar() {
    TopAppBar(
        title = {
            Text(
                text = "Shifts",
                style = MaterialTheme.typography.titleLarge
            )
        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ShiftsFilterBar(
    filterState: ShiftFilterState?,
    onStartDateClick: () -> Unit,
    onEndDateClick: () -> Unit,
    onAdvancedFilterClick: () -> Unit
) {
    val formatter = remember { DateTimeFormatter.ofPattern("dd MMM yy") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        FilterChip(
            label = "From - ${filterState?.startDate?.format(formatter)}",
            onClick = onStartDateClick
        )

        Spacer(modifier = Modifier.width(8.dp))

        FilterChip(
            label = "To - ${filterState?.endDate?.format(formatter)}",
            onClick = onEndDateClick
        )

        Spacer(modifier = Modifier.weight(1f))

        TextButton(onClick = onAdvancedFilterClick) {
            Text("Advanced")
        }
    }
}

@Composable
private fun FilterChip(
    label: String,
    onClick: () -> Unit
) {
    OutlinedButton(onClick = onClick) {
        Text(text = label)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ShiftListContent(
    shifts: List<ShiftEntity>,
    modifier: Modifier = Modifier
) {
    if (shifts.isEmpty()) {
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No shifts available",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    } else {
        LazyColumn(
            modifier = modifier,
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(shifts.size) { index ->
                ShiftListItem(shifts[index])
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ShiftListItem(shift: ShiftEntity) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {

            Text(
                text = "${DateUtils.millisToDate(shift.startTimeMillis)} · " +
                        "${DateUtils.millisToTime(shift.startTimeMillis)} - " +
                        DateUtils.millisToTime(shift.endTimeMillis),
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = shift.location,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Status: ${shift.status}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

