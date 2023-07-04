package com.meal.planner.presentation.ui.dietCreate

import java.time.DayOfWeek

data class CreateDietUiState(
    val id: String? = null,
    val name: String = "",
    val daysOfWeek: ArrayList<DayOfWeek> = arrayListOf()
)

val CreateDietUiState.isEditMode get() = id != null