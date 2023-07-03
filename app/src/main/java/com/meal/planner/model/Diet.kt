package com.meal.planner.model

import java.time.DayOfWeek

data class Diet(
    val id: Int,
    val name: String,
    val meals: ArrayList<Meal>,
    var daysOfWeek: ArrayList<DayOfWeek>
)

fun Diet.toCalories() = this.meals.sumOf { it.toCalories() }

fun Diet.toProteins() = this.meals.sumOf { it.toProteins() }

fun Diet.toCarbs() = this.meals.sumOf { it.toCarbs() }

fun Diet.toFats() = this.meals.sumOf { it.toFats() }
