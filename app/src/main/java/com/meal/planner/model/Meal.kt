package com.meal.planner.model

data class Meal(
    val id: String,
    val name: String,
    val foods: ArrayList<Food>,
    var timing: Int
)

fun Meal.toCalories() = this.foods.sumOf { it.toCalories() }

fun Meal.toProteins() = this.foods.sumOf { it.toProteins() }

fun Meal.toCarbs() = this.foods.sumOf { it.toCarbs() }

fun Meal.toFats() = this.foods.sumOf { it.toFats() }