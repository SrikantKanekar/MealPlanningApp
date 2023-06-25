package com.meal.planner.presentation.ui.food

import androidx.lifecycle.ViewModel
import com.meal.planner.cache.database.DietDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class FoodViewModel @Inject constructor(
    private val dietDataSource: DietDataSource
) : ViewModel() {
    private val _uiState = MutableStateFlow(FoodUiState())
    val uiState: StateFlow<FoodUiState> = _uiState

    fun updateName(name: String) {
        _uiState.update { it.copy(name = name) }
    }

    fun updateProtein(protein: String) {
        _uiState.update { it.copy(protein = protein) }
    }

    fun updateCarb(carb: String) {
        _uiState.update { it.copy(carb = carb) }
    }

    fun updateFat(fat: String) {
        _uiState.update { it.copy(fat = fat) }
    }

    fun updateQuantity(quantity: String) {
        _uiState.update { it.copy(quantity = quantity) }
    }
}