package com.meal.planner.presentation.ui.meal

import androidx.lifecycle.ViewModel
import com.meal.planner.cache.database.DietDataSource
import com.meal.planner.model.Food
import com.meal.planner.model.Meal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MealViewModel @Inject constructor(
    private val dietDataSource: DietDataSource
) : ViewModel() {
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