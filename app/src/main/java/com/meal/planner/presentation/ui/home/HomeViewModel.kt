package com.meal.planner.presentation.ui.home

import androidx.lifecycle.ViewModel
import com.meal.planner.model.Diet
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import java.time.DayOfWeek

class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        _uiState.update {
            it.copy(
                diets = listOf(
                    Diet(
                        "Non veg",
                        listOf(),
                        listOf(
                            DayOfWeek.WEDNESDAY,
                            DayOfWeek.FRIDAY,
                            DayOfWeek.SATURDAY,
                            DayOfWeek.SUNDAY
                        )
                    ),
                    Diet(
                        "Veg",
                        listOf(),
                        listOf(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.THURSDAY)
                    ),
                )
            )
        }
    }
}