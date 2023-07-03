package com.meal.planner.presentation.ui.meal

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.NavigateNext
import androidx.compose.material.icons.outlined.Keyboard
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PlainTooltipBox
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.semantics.isContainer
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.meal.planner.model.toCalories
import com.meal.planner.presentation.components.TimePickerDialog
import com.meal.planner.presentation.ui.meal.components.MealDashboardCard
import com.meal.planner.util.capitalise
import com.meal.planner.util.roundToInt
import kotlinx.coroutines.launch
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MealScreen(
    viewModel: MealViewModel,
    dietId: String?,
    mealId: String?,
    navigateBack: () -> Unit,
    navigateToFood: (String?) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    var menuExpanded by remember { mutableStateOf(false) }

    var showTimePicker by remember { mutableStateOf(false) }
    val showingPicker = remember { mutableStateOf(true) }
    val snackState = remember { SnackbarHostState() }
    val snackScope = rememberCoroutineScope()
    val configuration = LocalConfiguration.current

    LaunchedEffect(Unit) {
        viewModel.loadMeal(dietId, mealId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "back"
                        )
                    }
                },
                title = { uiState.meal?.let { Text(text = it.name) } },
                actions = {
                    PlainTooltipBox(tooltip = { Text(text = "Time") }) {
                        IconButton(
                            modifier = Modifier.tooltipAnchor(),
                            onClick = { showTimePicker = true }
                        ) {
                            Icon(
                                imageVector = Icons.Default.AccessTime,
                                contentDescription = "Time"
                            )
                        }
                    }

                    Box {
                        PlainTooltipBox(tooltip = { Text(text = "More") }) {
                            IconButton(
                                modifier = Modifier.tooltipAnchor(),
                                onClick = { menuExpanded = true }
                            ) {
                                Icon(
                                    Icons.Default.MoreVert,
                                    contentDescription = "More"
                                )
                            }
                        }
                        DropdownMenu(
                            expanded = menuExpanded,
                            onDismissRequest = { menuExpanded = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Delete Meal") },
                                onClick = {
                                    menuExpanded = false
                                    viewModel.deleteMeal(dietId)
                                    navigateBack()
                                }
                            )
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navigateToFood(null) }) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add Food"
                )
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackState)
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 18.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            if (uiState.meal != null) {
                val foods = uiState.meal!!.foods

                item {
                    MealDashboardCard(uiState = uiState)
                }

                item {
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = if (foods.isNotEmpty()) "Foods" else "Add Foods",
                        style = MaterialTheme.typography.labelLarge
                    )
                }

                items(foods) { food ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { navigateToFood(food.id) }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 14.dp, horizontal = 20.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = food.name.capitalise(),
                                style = MaterialTheme.typography.titleMedium
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = food.toCalories().roundToInt().toString() + " cal",
                                    style = MaterialTheme.typography.labelLarge
                                )
                                Spacer(modifier = Modifier.width(5.dp))
                                Icon(
                                    imageVector = Icons.Default.NavigateNext,
                                    contentDescription = "Edit Food"
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    if (showTimePicker) {
        TimePickerDialog(
            title = if (showingPicker.value) "Select Time " else "Enter Time",
            onCancel = { showTimePicker = false },
            onConfirm = {
                viewModel.updateMealTime(dietId)
                val time = LocalTime
                    .of(uiState.timePickerState.hour, uiState.timePickerState.minute)
                    .format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT))
                snackScope.launch {
                    snackState.showSnackbar("Updated time: $time")
                }
                showTimePicker = false
            },
            toggle = {
                if (configuration.screenHeightDp > 400) {
                    Box(
                        Modifier
                            .fillMaxSize()
                            .semantics {
                                isContainer = true
                            }
                    ) {
                        IconButton(
                            modifier = Modifier
                                .size(64.dp, 72.dp)
                                .align(Alignment.BottomStart)
                                .zIndex(5f),
                            onClick = { showingPicker.value = !showingPicker.value }) {
                            val icon = if (showingPicker.value) {
                                Icons.Outlined.Keyboard
                            } else {
                                Icons.Outlined.Schedule
                            }
                            Icon(
                                icon,
                                contentDescription = if (showingPicker.value) {
                                    "Switch to Text Input"
                                } else {
                                    "Switch to Touch Input"
                                }
                            )
                        }
                    }
                }
            }
        ) {
            if (showingPicker.value && configuration.screenHeightDp > 400) {
                TimePicker(state = uiState.timePickerState)
            } else {
                TimeInput(state = uiState.timePickerState)
            }
        }
    }
}