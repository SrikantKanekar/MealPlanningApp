package com.meal.planner.presentation.navigation

sealed class NavigationRoute(
    val route: String
) {
    object EnterWeight : NavigationRoute("EnterWeight")

    object DietType : NavigationRoute("DietType")

    object Home : NavigationRoute("Home")

    object Diet : NavigationRoute("Diet")

    object Meal : NavigationRoute("Meal")

    object Food : NavigationRoute("Food")

    object Settings : NavigationRoute("Settings")
}
