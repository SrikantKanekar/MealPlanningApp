package com.meal.planner.presentation.navigation

sealed class NavigationRoute(
    val route: String
) {
    object EnterWeight : NavigationRoute("EnterWeight")

    object DietType : NavigationRoute("DietType")

    object Home : NavigationRoute("Home")

    class CreateDiet(id: String? = null) :
        NavigationRoute(if (id == null) "CreateDiet/{id}" else "CreateDiet/$id")

    class Diet(id: String? = null) : NavigationRoute(if (id == null) "Diet/{id}" else "Diet/$id")

    class CreateMeal(
        dietId: String? = null,
        mealId: String? = null
    ) :
        NavigationRoute(
            if (dietId == null)
                "CreateMeal/{dietId}/{mealId}"
            else
                "CreateMeal/$dietId/$mealId"
        )

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
            if (dietId == null || mealId == null)
                "Food/{dietId}/{mealId}/{foodId}"
            else
                "Food/$dietId/$mealId/$foodId"
        )

    object Settings : NavigationRoute("Settings")
}
