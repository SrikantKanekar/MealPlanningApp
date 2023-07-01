package com.meal.planner.util

import com.meal.planner.model.enums.DietType

fun Double.proteinToCalories() = this * 4

fun Double.carbToCalories() = this * 4

fun Double.fatToCalories() = this * 9

fun targetCalories(weight: Double, dietType: DietType) =
    targetProteins(weight, dietType).proteinToCalories() +
            targetCarbs(weight, dietType).carbToCalories() +
            targetFats(weight, dietType).fatToCalories()

fun targetProteins(weight: Double, dietType: DietType) = when (dietType) {
    DietType.BULKING -> weight
    DietType.CUTTING -> weight * 1.2
    DietType.MAINTAINING -> weight
    DietType.NULL -> 0.0
}

fun targetCarbs(weight: Double, dietType: DietType) = when (dietType) {
    DietType.BULKING -> weight * 2
    DietType.CUTTING -> weight
    DietType.MAINTAINING -> weight * 1.5
    DietType.NULL -> 0.0
}

fun targetFats(weight: Double, dietType: DietType) = when (dietType) {
    DietType.BULKING -> weight / 3
    DietType.CUTTING -> weight / 5
    DietType.MAINTAINING -> weight / 4
    DietType.NULL -> 0.0
}