package com.meal.planner.presentation.ui.diet

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.meal.planner.model.toCalories
import com.meal.planner.presentation.components.DayOfWeekButton
import com.meal.planner.util.capitalise
import java.time.DayOfWeek
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DietScreen(
    viewModel: DietViewModel,
    dietId: String?,
    navigateBack: () -> Unit,
    navigateToCreateMeal: (String) -> Unit,
    navigateToMeal: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    var dialogOpened by remember { mutableStateOf(false) }
    var menuExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.loadDiet(dietId)
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
                title = { uiState.diet?.let { Text(text = it.name) } },
                actions = {
                    IconButton(onClick = { dialogOpened = true }) {
                        Icon(
                            imageVector = Icons.Filled.DateRange,
                            contentDescription = "Select days"
                        )
                    }

                    Box {
                        IconButton(onClick = { menuExpanded = true }) {
                            Icon(
                                Icons.Default.MoreVert,
                                contentDescription = "More options"
                            )
                        }
                        DropdownMenu(
                            expanded = menuExpanded,
                            onDismissRequest = { menuExpanded = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Delete Diet") },
                                onClick = {
                                    menuExpanded = false
                                    viewModel.deleteDiet(dietId)
                                    navigateBack()
                                }
                            )
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { dietId?.let { navigateToCreateMeal(it) } }) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add meal"
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 18.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            if (uiState.diet != null) {
                val meals = uiState.diet!!.meals.sortedBy { it.timing }
                items(meals) { meal ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { navigateToMeal(meal.id) }
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 4.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = meal.name.capitalise(),
                                    style = MaterialTheme.typography.titleLarge
                                )
                                Text(
                                    text = LocalTime
                                        .ofSecondOfDay(meal.timing.toLong())
                                        .format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)),
                                    style = MaterialTheme.typography.labelLarge
                                )
                            }
                            meal.foods.map { food ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = food.name.capitalise(),
                                        style = MaterialTheme.typography.labelLarge
                                    )
                                    Text(
                                        text = food.toCalories().toInt().toString() + " cal",
                                        style = MaterialTheme.typography.labelLarge
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (dialogOpened) {
        AlertDialog(
            onDismissRequest = { dialogOpened = false },
            title = { Text(text = "Select days") },
            text = {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(5),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    items(DayOfWeek.values()) { day ->
                        DayOfWeekButton(
                            dayOfWeek = day,
                            onClick = { viewModel.toggleDay(day) },
                            enabled = uiState.daysOfWeek.contains(day)
                        )
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = { dialogOpened = false }
                ) {
                    Text("Close")
                }
            }
        )
    }
}