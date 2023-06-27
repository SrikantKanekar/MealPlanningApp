package com.meal.planner.presentation.ui.mealCreate

import java.time.LocalTime

data class CreateMealUiState(
    val name: String = "",
    val timing: LocalTime = LocalTime.MIN
)
