package com.meal.planner.presentation.ui.diet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meal.planner.cache.database.DietDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import javax.inject.Inject

@HiltViewModel
class DietViewModel @Inject constructor(
    private val dietDataSource: DietDataSource
) : ViewModel() {
    private val _uiState = MutableStateFlow(DietUiState())
    val uiState: StateFlow<DietUiState> = _uiState

    fun loadDiet(id: String?) {
        viewModelScope.launch {
            if (id != null) {
                dietDataSource.dietFlow(id).collect { diet ->
                    _uiState.update {
                        it.copy(
                            diet = diet,
                            daysOfWeek = diet?.daysOfWeek ?: listOf()
                        )
                    }
                }
            }
        }
    }

    fun toggleDay(day: DayOfWeek) {
        viewModelScope.launch {
            val diet = _uiState.value.diet
            if (diet != null) {
                val days = diet.daysOfWeek
                if (days.contains(day)) {
                    days.remove(day)
                } else {
                    days.add(day)
                    days.sort()
                }
                diet.daysOfWeek = days

                _uiState.update {
                    it.copy(
                        diet = diet,
                        daysOfWeek = days
                    )
                }

                clearDaysOfOtherDiets()
                dietDataSource.updateDiets(listOf(diet))
            }
        }
    }

    private suspend fun clearDaysOfOtherDiets() {
        val diets = dietDataSource.getAllDiets()

        if (diets != null) {
            diets
                .filter { it.id != _uiState.value.diet?.id }
                .forEach { diet ->
                    diet.daysOfWeek.removeIf { _uiState.value.daysOfWeek.contains(it) }
                }
            dietDataSource.updateDiets(diets)
        }
    }

    fun deleteDiet(id: String?) {
        viewModelScope.launch {
            if (id != null) {
                dietDataSource.deleteDiets(listOf(id))
            }
        }
    }
}