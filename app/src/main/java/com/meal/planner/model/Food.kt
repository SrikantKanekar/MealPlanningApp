package com.meal.planner.model

import com.meal.planner.util.carbToCalories
import com.meal.planner.util.fatToCalories
import com.meal.planner.util.proteinToCalories

data class Food(
    val id: String,
    var name: String,
    var proteins: Double,
    var carbs: Double,
    var fats: Double,
    var quantity: Double
)

fun Food.toCalories() =
    (proteins.proteinToCalories() + carbs.carbToCalories() + fats.fatToCalories()) * quantity / 100

fun Food.toProteins() = proteins * quantity / 100

fun Food.toCarbs() = carbs * quantity / 100

fun Food.toFats() = fats * quantity / 100
