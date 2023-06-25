package com.meal.planner.cache.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.meal.planner.model.Meal
import java.time.DayOfWeek

@Entity(tableName = "diet_table")
data class DietEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val meals: List<Meal>,
    val daysOfWeek: List<DayOfWeek>
)