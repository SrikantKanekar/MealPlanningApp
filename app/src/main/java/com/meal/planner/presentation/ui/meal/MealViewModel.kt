package com.meal.planner.presentation.ui.meal

import androidx.lifecycle.ViewModel
import com.meal.planner.model.Food
import com.meal.planner.model.Meal
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class MealViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MealUiState())
    val uiState: StateFlow<MealUiState> = _uiState

    init {
        _uiState.update {
            it.copy(
                meal = Meal(
                    "Morning",
                    listOf(
                        Food("Smoothie", 1.0, 1.0, 1.0, 1.0),
                        Food("Banana", 1.0, 1.0, 1.0, 1.0)
                    ),
                    "7:30 AM"
                )
            )
        }
    }
}