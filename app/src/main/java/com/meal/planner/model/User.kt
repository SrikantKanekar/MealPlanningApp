package com.meal.planner.model

import com.meal.planner.model.enums.DietType

data class User(
    val weight: Double,
    val dietType: DietType
)
