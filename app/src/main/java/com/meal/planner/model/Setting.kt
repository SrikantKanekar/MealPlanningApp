package com.meal.planner.model

import com.meal.planner.model.enums.DietType

data class Setting(
    val weight: Double,
    val dietType: DietType
)
