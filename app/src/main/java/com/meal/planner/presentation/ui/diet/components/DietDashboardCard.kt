package com.meal.planner.presentation.ui.diet.components

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
import com.meal.planner.model.toCalories
import com.meal.planner.model.toCarbs
import com.meal.planner.model.toFats
import com.meal.planner.model.toProteins
import com.meal.planner.presentation.components.Indicator
import com.meal.planner.presentation.components.IndicatorSize
import com.meal.planner.presentation.ui.diet.DietUiState
import com.meal.planner.util.capitalise
import com.meal.planner.util.kgToPounds
import com.meal.planner.util.roundToInt
import com.meal.planner.util.targetCalories
import com.meal.planner.util.targetCarbs
import com.meal.planner.util.targetFats
import com.meal.planner.util.targetProteins

@Composable
fun DietDashboardCard(
    uiState: DietUiState
) {
    val diet = uiState.diet!!

    val currentCalories = diet.toCalories().roundToInt()
    val currentProteins = diet.toProteins().roundToInt()
    val currentCarbs = diet.toCarbs().roundToInt()
    val currentFats = diet.toFats().roundToInt()

    val targetCalories = targetCalories(uiState.weight.kgToPounds(), uiState.dietType).roundToInt()
    val targetProteins = targetProteins(uiState.weight.kgToPounds(), uiState.dietType).roundToInt()
    val targetCarbs = targetCarbs(uiState.weight.kgToPounds(), uiState.dietType).roundToInt()
    val targetFats = targetFats(uiState.weight.kgToPounds(), uiState.dietType).roundToInt()

    Card(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = uiState.diet.name.capitalise() + " diet",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Indicator(
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
                Indicator(
                    name = "Proteins",
                    unit = "g",
                    current = currentProteins,
                    target = targetProteins,
                    size = IndicatorSize.MEDIUM
                )

                Indicator(
                    name = "Carbs",
                    unit = "g",
                    current = currentCarbs,
                    target = targetCarbs,
                    size = IndicatorSize.MEDIUM
                )

                Indicator(
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