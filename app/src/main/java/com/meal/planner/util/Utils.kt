package com.meal.planner.util

import java.util.Locale
import kotlin.math.roundToInt

fun String.capitalise() = this.replaceFirstChar {
    when {
        it.isLowerCase() -> it.titlecase(Locale.ROOT)
        else -> it.toString()
    }
}

fun Double.roundToInt(): Int {
    return try {
        this.roundToInt()
    } catch (e: Exception) {
        this.toInt()
    }
}
