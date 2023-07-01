package com.meal.planner.model.enums

import com.meal.planner.util.capitalise

enum class DietType {
    BULKING, CUTTING, MAINTAINING, NULL
}

fun DietType.toName() = this.name.lowercase().capitalise()