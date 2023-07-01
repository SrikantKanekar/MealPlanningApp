package com.meal.planner.model

import java.time.DayOfWeek

data class Diet(
    val id: Int,
    val name: String,
    val meals: ArrayList<Meal>,
    var daysOfWeek: ArrayList<DayOfWeek>
)

fun Diet.toCalories() = this.meals.sumOf { it.toCalories() } * daysOfWeek.size

fun Diet.toProteins() = this.meals.sumOf { it.toProteins() } * daysOfWeek.size

fun Diet.toCarbs() = this.meals.sumOf { it.toCarbs() } * daysOfWeek.size

fun Diet.toFats() = this.meals.sumOf { it.toFats() } * daysOfWeek.size
