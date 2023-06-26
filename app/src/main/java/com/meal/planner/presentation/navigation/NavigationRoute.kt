package com.meal.planner.presentation.navigation

import com.meal.planner.model.enums.FoodScreenType

sealed class NavigationRoute(
    val route: String
) {
    object EnterWeight : NavigationRoute("EnterWeight")

    object DietType : NavigationRoute("DietType")

    object Home : NavigationRoute("Home")

    object CreateDiet : NavigationRoute("CreateDiet")

    class Diet(id: String? = null) : NavigationRoute(if (id == null) "Diet/{id}" else "Diet/$id")

    class Meal(dietId: String? = null, mealId: String? = null) :
        NavigationRoute(if (dietId == null || mealId == null) "Meal/{dietId}/{mealId}" else "Meal/$dietId/$mealId")

    class Food(foodScreenType: FoodScreenType? = null) :
        NavigationRoute(if (foodScreenType == null) "Food/{type}" else "Food/$foodScreenType")

    object Settings : NavigationRoute("Settings")
}
