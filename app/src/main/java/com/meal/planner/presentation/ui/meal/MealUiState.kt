package com.meal.planner.presentation.ui.meal

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import com.meal.planner.model.Meal

@OptIn(ExperimentalMaterial3Api::class)
data class MealUiState(
    val meal: Meal? = null,
    val timePickerState: TimePickerState = TimePickerState(0, 0, false)
)