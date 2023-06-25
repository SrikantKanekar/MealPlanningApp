package com.meal.planner.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.meal.planner.model.enums.FoodScreenType
import com.meal.planner.presentation.ui.diet.DietScreen
import com.meal.planner.presentation.ui.diet.DietViewModel
import com.meal.planner.presentation.ui.dietCreate.CreateDietScreen
import com.meal.planner.presentation.ui.dietCreate.CreateDietViewModel
import com.meal.planner.presentation.ui.food.FoodScreen
import com.meal.planner.presentation.ui.food.FoodViewModel
import com.meal.planner.presentation.ui.home.HomeScreen
import com.meal.planner.presentation.ui.home.HomeViewModel
import com.meal.planner.presentation.ui.meal.MealScreen
import com.meal.planner.presentation.ui.meal.MealViewModel
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

        composable(route = NavigationRoute.Home.route) { backStackEntry ->
            val homeViewModel = hiltViewModel<HomeViewModel>(backStackEntry)
            HomeScreen(
                viewModel = homeViewModel,
                navigateToDiet = { navController.navigate(NavigationRoute.Diet.route) },
                navigateToCreateDiet = { navController.navigate(NavigationRoute.CreateDiet.route) },
                navigateToSettings = { navController.navigate(NavigationRoute.Settings.route) }
            )
        }

        composable(route = NavigationRoute.CreateDiet.route) { backStackEntry ->
            val createDietViewModel = hiltViewModel<CreateDietViewModel>(backStackEntry)
            CreateDietScreen(
                viewModel = createDietViewModel,
                navigateBack = { navController.popBackStack() },
            )
        }

        composable(route = NavigationRoute.Diet.route) { backStackEntry ->
            val dietViewModel = hiltViewModel<DietViewModel>(backStackEntry)
            DietScreen(
                viewModel = dietViewModel,
                navigateBack = { navController.popBackStack() },
                navigateToMeal = { navController.navigate(NavigationRoute.Meal.route) }
            )
        }

        composable(route = NavigationRoute.Meal.route) { backStackEntry ->
            val mealViewModel = hiltViewModel<MealViewModel>(backStackEntry)
            MealScreen(
                viewModel = mealViewModel,
                navigateBack = { navController.popBackStack() },
                navigateToFoodAdd = { navController.navigate(NavigationRoute.Food(FoodScreenType.ADD).route) },
                navigateToFoodEdit = { navController.navigate(NavigationRoute.Food(FoodScreenType.EDIT).route) }
            )
        }

        composable(route = NavigationRoute.Food().route) { backStackEntry ->
            val foodViewModel = hiltViewModel<FoodViewModel>(backStackEntry)
            val type = backStackEntry.arguments?.getString("type")
            val foodScreenType = if (type == "ADD") FoodScreenType.ADD else FoodScreenType.EDIT
            FoodScreen(
                viewModel = foodViewModel,
                navigateBack = { navController.popBackStack() },
                foodScreenType = foodScreenType
            )
        }

        composable(route = NavigationRoute.Settings.route) {
            SettingsScreen()
        }
    }
}