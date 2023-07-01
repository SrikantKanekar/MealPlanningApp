package com.meal.planner.presentation.ui.settings

import com.meal.planner.model.enums.DietType

data class SettingsUiState(
    val weight: String = "",
    val dietType: DietType = DietType.BULKING
)