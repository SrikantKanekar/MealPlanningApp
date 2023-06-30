package com.meal.planner.presentation.ui.diet

import com.meal.planner.model.Diet
import java.time.DayOfWeek

data class DietUiState(
    val diet: Diet? = null,
    val daysOfWeek: List<DayOfWeek> = listOf()
)