package com.meal.planner.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.meal.planner.model.enums.FoodScreenType
import com.meal.planner.presentation.ui.diet.DietScreen
import com.meal.planner.presentation.ui.food.FoodScreen
import com.meal.planner.presentation.ui.home.CreateDietScreen
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
        startDestination = NavigationRoute.Home.route
    ) {
        composable(route = NavigationRoute.EnterWeight.route) {
            EnterWeightScreen(
                navigate = { navController.navigate(NavigationRoute.DietType.route) }
            )
        }

        composable(route = NavigationRoute.DietType.route) {
            DietTypeScreen(
                navigate = { navController.navigate(NavigationRoute.Home.route) }
            )
        }

        composable(route = NavigationRoute.Home.route) {
            HomeScreen(
                navigateToDiet = { navController.navigate(NavigationRoute.Diet.route) },
                navigateToCreateDiet = { navController.navigate(NavigationRoute.CreateDiet.route) },
                navigateToSettings = { navController.navigate(NavigationRoute.Settings.route) }
            )
        }

        composable(route = NavigationRoute.CreateDiet.route) {
            CreateDietScreen(
                navigateBack = { navController.popBackStack() },
            )
        }

        composable(route = NavigationRoute.Diet.route) {
            DietScreen(
                navigateBack = { navController.popBackStack() },
                navigateToMeal = { navController.navigate(NavigationRoute.Meal.route) }
            )
        }

        composable(route = NavigationRoute.Meal.route) {
            MealScreen(
                navigateBack = { navController.popBackStack() },
                navigateToFoodAdd = { navController.navigate(NavigationRoute.Food(FoodScreenType.ADD).route) },
                navigateToFoodEdit = { navController.navigate(NavigationRoute.Food(FoodScreenType.EDIT).route) }
            )
        }

        composable(route = NavigationRoute.Food().route) { backStackEntry ->
            val type = backStackEntry.arguments?.getString("type")
            val foodScreenType = if (type == "ADD") FoodScreenType.ADD else FoodScreenType.EDIT
            FoodScreen(
                navigateBack = { navController.popBackStack() },
                foodScreenType = foodScreenType
            )
        }

        composable(route = NavigationRoute.Settings.route) {
            SettingsScreen()
        }
    }
}