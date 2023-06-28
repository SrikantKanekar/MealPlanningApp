package com.meal.planner.presentation.ui.meal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meal.planner.cache.database.DietDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

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
                        _uiState.update {
                            it.copy(
                                meal = diet.meals.find { meal ->
                                    meal.id == mealId
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    fun updateMealTime(dietId: String?, value: Int) {
        viewModelScope.launch {
            if (dietId != null) {
                val diet = dietDataSource.getDiet(dietId)

                if (diet != null) {
                    diet.meals
                        .first { it.id == _uiState.value.meal?.id }
                        .apply { timing = value }
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