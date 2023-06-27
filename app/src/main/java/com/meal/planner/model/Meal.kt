package com.meal.planner.model

data class Meal(
    val id: String,
    val name: String,
    val foods: ArrayList<Food>,
    val timing: Int
)
