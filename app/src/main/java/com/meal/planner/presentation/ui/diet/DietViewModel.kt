package com.meal.planner.presentation.ui.diet

import androidx.lifecycle.ViewModel
import com.meal.planner.model.Diet
import com.meal.planner.model.Food
import com.meal.planner.model.Meal
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import java.time.DayOfWeek

class DietViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(DietUiState())
    val uiState: StateFlow<DietUiState> = _uiState

    init {
        _uiState.update {
            it.copy(
                diet = Diet(
                    "Non veg",
                    listOf(
                        Meal(
                            "Morning",
                            listOf(
                                Food("Smoothie", 1.0, 1.0, 1.0, 1.0),
                                Food("Banana", 1.0, 1.0, 1.0, 1.0)
                            ),
                            "7:30 AM"
                        ),
                        Meal(
                            "Lunch",
                            listOf(
                                Food("Quinoa", 1.0, 1.0, 1.0, 1.0),
                                Food("Salad", 1.0, 1.0, 1.0, 1.0)
                            ),
                            "1:30 PM"
                        )
                    ),
                    listOf(
                        DayOfWeek.WEDNESDAY,
                        DayOfWeek.FRIDAY,
                        DayOfWeek.SATURDAY,
                        DayOfWeek.SUNDAY
                    )
                )
            )
        }
    }
}