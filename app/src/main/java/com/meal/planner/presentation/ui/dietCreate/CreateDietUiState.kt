package com.meal.planner.presentation.ui.dietCreate

import java.time.DayOfWeek

data class CreateDietUiState(
    val name: String = "",
    val daysOfWeek: ArrayList<DayOfWeek> = arrayListOf()
)
