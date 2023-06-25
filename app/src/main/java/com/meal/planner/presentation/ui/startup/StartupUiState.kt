package com.meal.planner.presentation.ui.startup

import com.meal.planner.model.enums.DietType

data class StartupUiState(
    val weight: String = "",
    val dietType: DietType = DietType.BULKING
)