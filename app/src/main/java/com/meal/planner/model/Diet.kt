package com.meal.planner.model

import java.time.DayOfWeek

data class Diet(
    val id: Int,
    val name: String,
    val meals: List<Meal>,
    val daysOfWeek: List<DayOfWeek>
)
