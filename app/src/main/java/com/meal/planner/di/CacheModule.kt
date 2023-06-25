package com.meal.planner.di

import com.meal.planner.cache.database.DietDao
import com.meal.planner.cache.database.DietDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {

    @Singleton
    @Provides
    fun provideDietDao(dietDatabase: DietDatabase): DietDao {
        return dietDatabase.dietDao()
    }
}