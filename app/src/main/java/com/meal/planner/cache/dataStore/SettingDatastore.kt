package com.meal.planner.cache.dataStore

import android.content.Context
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.meal.planner.model.enums.DietType
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val weightKey = doublePreferencesKey("weight")
    private val dietTypeKey = stringPreferencesKey("dietType")

    val weightFlow: Flow<Double> = context.settingDataStore.data
        .map { settings ->
            settings[weightKey] ?: 0.0
        }

    val dietTypeFlow: Flow<DietType> = context.settingDataStore.data
        .map { settings ->
            DietType.valueOf(settings[dietTypeKey] ?: DietType.BULKING.name)
        }

    fun getWeight() = runBlocking {
        val settings = context.settingDataStore.data.first()
        settings[weightKey]
    }


    fun getDietType() = runBlocking {
        val settings = context.settingDataStore.data.first()
        DietType.valueOf(settings[dietTypeKey] ?: DietType.NULL.name)
    }

    suspend fun updateWeight(weight: Double) {
        context.settingDataStore.edit { settings ->
            settings[weightKey] = weight
        }
    }

    suspend fun updateDietType(dietType: DietType) {
        context.settingDataStore.edit { settings ->
            settings[dietTypeKey] = dietType.name
        }
    }
}