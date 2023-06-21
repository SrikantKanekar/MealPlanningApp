package com.meal.planner.presentation.navigation

import com.meal.planner.model.enums.FoodScreenType

sealed class NavigationRoute(
    val route: String
) {
    object EnterWeight : NavigationRoute("EnterWeight")

    object DietType : NavigationRoute("DietType")

    object Home : NavigationRoute("Home")

    object Diet : NavigationRoute("Diet")

    object Meal : NavigationRoute("Meal")

    class Food(foodScreenType: FoodScreenType? = null) :
        NavigationRoute(if (foodScreenType == null) "Food/{type}" else "Food/$foodScreenType")

    object Settings : NavigationRoute("Settings")
}
