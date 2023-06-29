package com.meal.planner.util

import java.time.DayOfWeek

// Timeouts
const val CACHE_TIMEOUT = 3000L

// Cache errors
const val CACHE_ERROR_TIMEOUT = "Cache timeout"
const val CACHE_ERROR_UNKNOWN = "Unknown cache error"

val dayMap = mapOf(
    DayOfWeek.MONDAY to "Mon",
    DayOfWeek.TUESDAY to "Tue",
    DayOfWeek.WEDNESDAY to "Wed",
    DayOfWeek.THURSDAY to "Thu",
    DayOfWeek.FRIDAY to "Fri",
    DayOfWeek.SATURDAY to "Sat",
    DayOfWeek.SUNDAY to "Sun",
)

