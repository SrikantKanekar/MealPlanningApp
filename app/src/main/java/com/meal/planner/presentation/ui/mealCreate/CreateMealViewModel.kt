package com.meal.planner.presentation.ui.mealCreate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meal.planner.cache.database.DietDataSource
import com.meal.planner.model.Meal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalTime
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CreateMealViewModel @Inject constructor(
    private val dietDataSource: DietDataSource
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateMealUiState())
    val uiState: StateFlow<CreateMealUiState> = _uiState

    fun updateName(name: String) {
        _uiState.update { it.copy(name = name) }
    }

    fun updateTime(timing: LocalTime) {
        _uiState.update { it.copy(timing = timing) }
    }

    fun createMeal(dietId: String?) {
        if (dietId != null) {
            viewModelScope.launch {
                val diet = dietDataSource.getDiet(dietId)
                if (diet != null) {

                    diet.meals.add(
                        Meal(
                            id = UUID.randomUUID().toString(),
                            name = _uiState.value.name,
                            foods = arrayListOf(),
                            timing = _uiState.value.timing.second
                        )
                    )

                    dietDataSource.updateDiets(
                        listOf(diet)
                    )
                }
            }
        }
    }
}