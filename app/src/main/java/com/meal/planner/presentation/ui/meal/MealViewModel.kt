package com.meal.planner.presentation.ui.meal

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meal.planner.cache.database.DietDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalTime
import javax.inject.Inject

@OptIn(ExperimentalMaterial3Api::class)
@HiltViewModel
class MealViewModel @Inject constructor(
    private val dietDataSource: DietDataSource
) : ViewModel() {
    private val _uiState = MutableStateFlow(MealUiState())
    val uiState: StateFlow<MealUiState> = _uiState

    fun loadMeal(dietId: String?, mealId: String?) {
        viewModelScope.launch {
            if (dietId != null && mealId != null) {
                dietDataSource.dietFlow(dietId).collect { diet ->
                    if (diet != null) {
                        val meal = diet.meals.find { meal -> meal.id == mealId }
                        val localTime = LocalTime.ofSecondOfDay(meal?.timing?.toLong() ?: 0)

                        _uiState.update {
                            it.copy(
                                meal = meal,
                                timePickerState = TimePickerState(
                                    localTime.hour,
                                    localTime.minute,
                                    false
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    fun updateMealTime(dietId: String?) {
        viewModelScope.launch {
            if (dietId != null) {
                val diet = dietDataSource.getDiet(dietId)

                if (diet != null) {
                    diet.meals
                        .first { it.id == _uiState.value.meal?.id }
                        .apply {
                            val timePickerState = _uiState.value.timePickerState
                            val seconds = LocalTime
                                .of(timePickerState.hour, timePickerState.minute)
                                .toSecondOfDay()
                            timing = seconds
                        }
                    dietDataSource.updateDiets(listOf(diet))
                }
            }
        }
    }

    fun deleteMeal(dietId: String?) {
        viewModelScope.launch {
            if (dietId != null) {
                val diet = dietDataSource.getDiet(dietId)

                if (diet != null) {
                    diet
                        .meals
                        .removeIf { it.id == _uiState.value.meal?.id }

                    dietDataSource.updateDiets(listOf(diet))
                }
            }
        }
    }
}