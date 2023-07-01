package com.meal.planner.presentation.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meal.planner.cache.dataStore.SettingDataStore
import com.meal.planner.model.enums.DietType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingDataStore: SettingDataStore
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState

    init {
        viewModelScope.launch {
            settingDataStore.dietTypeFlow.collect { dietType ->
                _uiState.update { it.copy(dietType = dietType) }
            }
        }
        viewModelScope.launch {
            settingDataStore.weightFlow.collect { weight ->
                _uiState.update { it.copy(weight = weight.toString()) }
            }
        }
    }

    fun updateWeight(weight: String) {
        _uiState.update { it.copy(weight = weight) }
    }

    fun saveWeight() {
        viewModelScope.launch {
            settingDataStore.updateWeight(_uiState.value.weight.toDouble())
        }
    }

    fun resetWeight() {
        viewModelScope.launch {
            val weight = settingDataStore.getWeight()
            _uiState.update { it.copy(weight = (weight ?: 0).toString()) }
        }
    }

    fun updateDietType(dietType: DietType) {
        viewModelScope.launch {
            settingDataStore.updateDietType(dietType)
        }
    }
}