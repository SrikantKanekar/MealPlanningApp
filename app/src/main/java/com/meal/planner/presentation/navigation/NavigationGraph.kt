package com.meal.planner.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
import com.meal.planner.presentation.ui.startup.StartupViewModel

@Composable
fun NavigationGraph(
    startDestination: String
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = NavigationRoute.EnterWeight.route) { backStackEntry ->
            val startupViewModel = hiltViewModel<StartupViewModel>(backStackEntry)
            EnterWeightScreen(
                viewModel = startupViewModel,
                navigate = { navController.navigate(NavigationRoute.DietType.route) }
            )
        }

        composable(route = NavigationRoute.DietType.route) { backStackEntry ->
            val startupViewModel = hiltViewModel<StartupViewModel>(backStackEntry)
            DietTypeScreen(
                viewModel = startupViewModel,
                navigate = { navController.navigate(NavigationRoute.Home.route) }
            )
        }

        composable(route = NavigationRoute.Home.route) { backStackEntry ->
            val homeViewModel = hiltViewModel<HomeViewModel>(backStackEntry)
            HomeScreen(
                viewModel = homeViewModel,
                navigateToDiet = { navController.navigate(NavigationRoute.Diet(it).route) },
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

        composable(route = NavigationRoute.Diet().route) { backStackEntry ->
            val dietViewModel = hiltViewModel<DietViewModel>(backStackEntry)
            val dietId = backStackEntry.arguments?.getString("id")
            DietScreen(
                viewModel = dietViewModel,
                dietId = dietId,
                navigateBack = { navController.popBackStack() },
                navigateToMeal = { navController.navigate(NavigationRoute.Meal(dietId, it).route) }
            )
        }

        composable(route = NavigationRoute.Meal().route) { backStackEntry ->
            val mealViewModel = hiltViewModel<MealViewModel>(backStackEntry)
            val dietId = backStackEntry.arguments?.getString("dietId")
            val mealId = backStackEntry.arguments?.getString("mealId")
            MealScreen(
                viewModel = mealViewModel,
                dietId = dietId,
                mealId = mealId,
                navigateBack = { navController.popBackStack() },
                navigateToFood = { navController.navigate(NavigationRoute.Food(dietId, mealId, it).route) },
            )
        }

        composable(route = NavigationRoute.Food().route) { backStackEntry ->
            val foodViewModel = hiltViewModel<FoodViewModel>(backStackEntry)
            val dietId = backStackEntry.arguments?.getString("dietId")
            val mealId = backStackEntry.arguments?.getString("mealId")
            val foodId = backStackEntry.arguments?.getString("foodId")
            FoodScreen(
                viewModel = foodViewModel,
                dietId = dietId,
                mealId = mealId,
                foodId = foodId,
                navigateBack = { navController.popBackStack() }
            )
        }

        composable(route = NavigationRoute.Settings.route) {
            SettingsScreen()
        }
    }
}