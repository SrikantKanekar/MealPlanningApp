package com.meal.planner.util

import java.util.Locale

fun String.capitalise() = this.replaceFirstChar {
    when {
        it.isLowerCase() -> it.titlecase(Locale.ROOT)
        else -> it.toString()
    }
}