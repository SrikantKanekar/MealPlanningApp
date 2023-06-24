package com.meal.planner.presentation.ui.dietCreate

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import java.time.DayOfWeek

class CreateDietViewModel: ViewModel() {

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
}