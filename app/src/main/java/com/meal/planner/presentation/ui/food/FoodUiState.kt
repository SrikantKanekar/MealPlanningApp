package com.meal.planner.presentation.ui.food

data class FoodUiState(
    val id: String? = null,
    val name: String = "",
    val protein: String = "",
    val carb: String = "",
    val fat: String = "",
    val quantity: String = "",
)

val FoodUiState.isEditMode get() = id != null