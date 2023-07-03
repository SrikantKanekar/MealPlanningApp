package com.meal.planner.presentation.ui.diet

import com.meal.planner.model.Diet
import com.meal.planner.model.enums.DietType
import java.time.DayOfWeek

data class DietUiState(
    val diet: Diet? = null,
    val daysOfWeek: List<DayOfWeek> = listOf(),
    val weight: Double = 0.0,
    val dietType: DietType = DietType.NULL
)