package com.meal.planner.presentation.ui.home

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.meal.planner.model.toCalories
import com.meal.planner.presentation.ui.home.components.DashboardCard
import com.meal.planner.util.capitalise
import com.meal.planner.util.dayMap

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    navigateToDiet: (String) -> Unit,
    navigateToCreateDiet: () -> Unit,
    navigateToSettings: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Home") },
                actions = {
                    IconButton(onClick = navigateToSettings) {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = "Settings"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = navigateToCreateDiet) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Create diet"
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

            val diets = uiState.diets.sortedBy { it.daysOfWeek.firstOrNull()?.value ?: 8 }

            item {
                DashboardCard(uiState = uiState, diets = diets)
            }

            item {
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = "Diets",
                    style = MaterialTheme.typography.labelLarge
                )
            }

            items(diets) { diet ->
                val interactionSource = remember { MutableInteractionSource() }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            interactionSource = interactionSource,
                            indication = LocalIndication.current,
                            onClick = { navigateToDiet(diet.id.toString()) }
                        ),
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 24.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = diet.name.capitalise(),
                                style = MaterialTheme.typography.titleLarge,
                                fontSize = 26.sp
                            )
                            Text(
                                text = diet
                                    .meals
                                    .sumOf { it.foods.sumOf { food -> food.toCalories() } }
                                    .toInt()
                                    .toString() + " cal",
                                style = MaterialTheme.typography.labelLarge
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.End
                        ) {
                            diet.daysOfWeek.map { day ->
                                AssistChip(
                                    modifier = Modifier.padding(horizontal = 1.dp),
                                    onClick = { navigateToDiet(diet.id.toString()) },
                                    label = {
                                        Text(
                                            text = dayMap[day]!!,
                                            style = MaterialTheme.typography.labelSmall,
                                            fontSize = 10.sp
                                        )
                                    },
                                    shape = MaterialTheme.shapes.medium,
                                    interactionSource = interactionSource
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}