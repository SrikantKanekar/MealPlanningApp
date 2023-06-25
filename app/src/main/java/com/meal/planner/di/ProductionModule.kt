package com.meal.planner.di

import android.content.Context
import androidx.room.Room
import com.meal.planner.cache.database.DietDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProductionModule {

    @Singleton
    @Provides
    fun provideDietDatabase(
        @ApplicationContext context: Context
    ): DietDatabase {
        return Room
            .databaseBuilder(context, DietDatabase::class.java, DietDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }
}