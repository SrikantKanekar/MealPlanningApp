package com.meal.planner.presentation.ui.home

import com.meal.planner.model.Diet

data class HomeUiState(
    val diets: List<Diet> = listOf()
)