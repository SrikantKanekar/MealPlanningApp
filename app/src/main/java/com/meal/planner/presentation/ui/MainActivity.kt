package com.meal.planner.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.meal.planner.cache.dataStore.SettingDataStore
import com.meal.planner.model.enums.DietType
import com.meal.planner.presentation.navigation.NavigationGraph
import com.meal.planner.presentation.navigation.NavigationRoute
import com.meal.planner.presentation.theme.MealPlannerTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var settingDataStore: SettingDataStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val startDestination = getStartDestination()

        setContent {
            MealPlannerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavigationGraph(startDestination)
                }
            }
        }
    }

    private fun getStartDestination(): String {
        settingDataStore.getWeight() ?: return NavigationRoute.EnterWeight.route

        return when (settingDataStore.getDietType()) {
            DietType.NULL -> NavigationRoute.DietType.route
            else -> NavigationRoute.Home.route
        }
    }
}