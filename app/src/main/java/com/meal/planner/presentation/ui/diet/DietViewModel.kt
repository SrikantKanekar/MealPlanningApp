package com.meal.planner.presentation.ui.diet

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
class DietViewModel @Inject constructor(
    private val dietDataSource: DietDataSource
) : ViewModel() {
    private val _uiState = MutableStateFlow(DietUiState())
    val uiState: StateFlow<DietUiState> = _uiState

    fun loadDiet(id: String?) {
        viewModelScope.launch {
            if (id != null) {
                dietDataSource.dietFlow(id).collect { diet ->
                    _uiState.update { it.copy(diet = diet) }
                }
            }
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