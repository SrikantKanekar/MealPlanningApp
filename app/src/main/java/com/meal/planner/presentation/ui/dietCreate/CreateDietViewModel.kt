package com.meal.planner.presentation.ui.dietCreate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meal.planner.cache.database.DietDataSource
import com.meal.planner.model.Diet
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import javax.inject.Inject

@HiltViewModel
class CreateDietViewModel @Inject constructor(
    private val dietDataSource: DietDataSource
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateDietUiState())
    val uiState: StateFlow<CreateDietUiState> = _uiState

    fun updateName(name: String) {
        _uiState.update { it.copy(name = name) }
    }

    fun toggleDay(day: DayOfWeek) {
        val days = ArrayList(uiState.value.daysOfWeek)
        if (days.contains(day)) {
            days.remove(day)
        } else {
            days.add(day)
        }
        _uiState.update { it.copy(daysOfWeek = days) }
    }

    fun createDiet() {
        viewModelScope.launch {
            dietDataSource.insertDiets(
                listOf(
                    Diet(
                        id = 0,
                        name = _uiState.value.name,
                        meals = arrayListOf(),
                        daysOfWeek = _uiState.value.daysOfWeek
                    )
                )
            )
        }
    }
}