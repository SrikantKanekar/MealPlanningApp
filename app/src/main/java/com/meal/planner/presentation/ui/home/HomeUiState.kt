package com.meal.planner.presentation.ui.home

import com.meal.planner.model.Diet
import com.meal.planner.model.enums.DietType

data class HomeUiState(
    val diets: List<Diet> = listOf(),
    val weight: Double = 0.0,
    val dietType: DietType = DietType.NULL
)