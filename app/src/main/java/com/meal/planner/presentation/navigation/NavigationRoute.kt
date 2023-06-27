package com.meal.planner.presentation.navigation

sealed class NavigationRoute(
    val route: String
) {
    object EnterWeight : NavigationRoute("EnterWeight")

    object DietType : NavigationRoute("DietType")

    object Home : NavigationRoute("Home")

    object CreateDiet : NavigationRoute("CreateDiet")

    class Diet(id: String? = null) : NavigationRoute(if (id == null) "Diet/{id}" else "Diet/$id")

    class Meal(
        dietId: String? = null,
        mealId: String? = null
    ) :
        NavigationRoute(
            if (dietId == null || mealId == null)
                "Meal/{dietId}/{mealId}"
            else
                "Meal/$dietId/$mealId"
        )

    class Food(
        dietId: String? = null,
        mealId: String? = null,
        foodId: String? = null
    ) :
        NavigationRoute(
            if (dietId == null || mealId == null || foodId == null)
                "Food/{dietId}/{mealId}/{foodId}"
            else
                "Food/$dietId/$mealId/$foodId"
        )

    object Settings : NavigationRoute("Settings")
}
