package com.meal.planner.model

data class Food(
    val id: String,
    var name: String,
    var proteins: Double,
    var carbs: Double,
    var fats: Double,
    var quantity: Double
)
