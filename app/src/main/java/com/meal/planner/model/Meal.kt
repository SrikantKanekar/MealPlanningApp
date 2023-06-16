package com.meal.planner.model

data class Meal(
    val name: String,
    val foods: List<Food>,
    val timing: String
)
