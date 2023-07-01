package com.meal.planner.presentation.ui.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.meal.planner.model.Diet
import com.meal.planner.model.enums.toName
import com.meal.planner.model.toCalories
import com.meal.planner.model.toCarbs
import com.meal.planner.model.toFats
import com.meal.planner.model.toProteins
import com.meal.planner.presentation.components.DashboardIndicator
import com.meal.planner.presentation.components.IndicatorSize
import com.meal.planner.presentation.ui.home.HomeUiState
import com.meal.planner.util.kgToPounds
import com.meal.planner.util.targetCalories
import com.meal.planner.util.targetCarbs
import com.meal.planner.util.targetFats
import com.meal.planner.util.targetProteins

@Composable
fun DashboardCard(
    uiState: HomeUiState,
    diets: List<Diet>
) {
    val totalDays = diets.sumOf { it.daysOfWeek.size }

    val currentCalories = (diets.sumOf { it.toCalories() } / totalDays).toInt()
    val currentProteins = (diets.sumOf { it.toProteins() } / totalDays).toInt()
    val currentCarbs = (diets.sumOf { it.toCarbs() } / totalDays).toInt()
    val currentFats = (diets.sumOf { it.toFats() } / totalDays).toInt()

    val targetCalories = targetCalories(uiState.weight.kgToPounds(), uiState.dietType).toInt()
    val targetProteins = targetProteins(uiState.weight.kgToPounds(), uiState.dietType).toInt()
    val targetCarbs = targetCarbs(uiState.weight.kgToPounds(), uiState.dietType).toInt()
    val targetFats = targetFats(uiState.weight.kgToPounds(), uiState.dietType).toInt()

    Card(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "Weekly average report",
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = "Diet: ${uiState.dietType.toName()}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Target weight: ${uiState.weight} kg",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                DashboardIndicator(
                    name = "Calories",
                    unit = "cal",
                    current = currentCalories,
                    target = targetCalories,
                    size = IndicatorSize.LARGE
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                DashboardIndicator(
                    name = "Proteins",
                    unit = "g",
                    current = currentProteins,
                    target = targetProteins,
                    size = IndicatorSize.MEDIUM
                )

                DashboardIndicator(
                    name = "Carbs",
                    unit = "g",
                    current = currentCarbs,
                    target = targetCarbs,
                    size = IndicatorSize.MEDIUM
                )

                DashboardIndicator(
                    name = "Fats",
                    unit = "g",
                    current = currentFats,
                    target = targetFats,
                    size = IndicatorSize.MEDIUM
                )
            }
        }
    }
}