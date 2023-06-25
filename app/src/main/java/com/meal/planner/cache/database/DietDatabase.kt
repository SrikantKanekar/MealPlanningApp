package com.meal.planner.cache.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [DietEntity::class], version = 1)
@TypeConverters(TypeConvertors::class)
abstract class DietDatabase : RoomDatabase() {

    abstract fun dietDao(): DietDao

    companion object {
        const val DATABASE_NAME: String = "diet-database"
    }
}