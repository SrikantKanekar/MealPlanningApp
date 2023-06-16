package com.meal.planner.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.meal.planner.presentation.ui.diet.DietScreen
import com.meal.planner.presentation.ui.food.FoodScreen
import com.meal.planner.presentation.ui.home.HomeScreen
import com.meal.planner.presentation.ui.meal.MealScreen
import com.meal.planner.presentation.ui.settings.SettingsScreen
import com.meal.planner.presentation.ui.startup.DietTypeScreen
import com.meal.planner.presentation.ui.startup.EnterWeightScreen

@Composable
fun NavigationGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavigationRoute.EnterWeight.route
    ) {
        composable(route = NavigationRoute.EnterWeight.route) {
            EnterWeightScreen(
                navigate = { navController.navigate(NavigationRoute.DietType.route) }
            )
        }

        composable(route = NavigationRoute.DietType.route) {
            DietTypeScreen()
        }

        composable(route = NavigationRoute.Home.route) {
            HomeScreen()
        }

        composable(route = NavigationRoute.Diet.route) {
            DietScreen()
        }

        composable(route = NavigationRoute.Meal.route) {
            MealScreen()
        }

        composable(route = NavigationRoute.Food.route) {
            FoodScreen()
        }

        composable(route = NavigationRoute.Settings.route) {
            SettingsScreen()
        }
    }
}