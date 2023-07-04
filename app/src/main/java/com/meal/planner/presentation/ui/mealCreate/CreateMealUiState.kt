package com.meal.planner.presentation.ui.mealCreate

import java.time.LocalTime

data class CreateMealUiState(
    val id: String? = null,
    val name: String = "",
    val timing: LocalTime = LocalTime.MIN
)

val CreateMealUiState.isEditMode get() = id != null