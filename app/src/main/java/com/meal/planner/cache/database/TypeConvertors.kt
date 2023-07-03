package com.meal.planner.cache.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.meal.planner.model.Meal
import java.time.DayOfWeek

class TypeConvertors {

    @TypeConverter
    fun fromMeal(meals: List<Meal>): String {
        return Gson().toJson(meals)
    }

    @TypeConverter
    fun toMeal(json: String): List<Meal> {
        val sType = object : TypeToken<List<Meal>>() {}.type
        return Gson().fromJson(json, sType)
    }

    @TypeConverter
    fun fromDayOfWeek(days: List<DayOfWeek>): String {
        return Gson().toJson(days)
    }

    @TypeConverter
    fun toDayOfWeek(json: String): List<DayOfWeek> {
        val sType = object : TypeToken<List<DayOfWeek>>() {}.type
        return Gson().fromJson(json, sType)
    }
}