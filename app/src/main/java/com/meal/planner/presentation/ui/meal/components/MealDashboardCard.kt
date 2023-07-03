package com.meal.planner.presentation.ui.meal.components

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
import com.meal.planner.presentation.components.FilledIndicator
import com.meal.planner.presentation.components.IndicatorSize
import com.meal.planner.presentation.ui.meal.MealUiState
import com.meal.planner.util.roundToInt

@Composable
fun MealDashboardCard(
    uiState: MealUiState
) {
    val meal = uiState.meal!!

    val calories = meal.toCalories().roundToInt()
    val proteins = meal.toProteins().roundToInt()
    val carbs = meal.toCarbs().roundToInt()
    val fats = meal.toFats().roundToInt()

    Card(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .padding(bottom = 10.dp)
        ) {
            Text(
                text = "Meal report",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                FilledIndicator(
                    name = "Calories",
                    unit = "cal",
                    value = calories,
                    size = IndicatorSize.SMALL
                )

                FilledIndicator(
                    name = "Proteins",
                    unit = "g",
                    value = proteins,
                    size = IndicatorSize.SMALL
                )

                FilledIndicator(
                    name = "Carbs",
                    unit = "g",
                    value = carbs,
                    size = IndicatorSize.SMALL
                )

                FilledIndicator(
                    name = "Fats",
                    unit = "g",
                    value = fats,
                    size = IndicatorSize.SMALL
                )
            }
        }
    }
}