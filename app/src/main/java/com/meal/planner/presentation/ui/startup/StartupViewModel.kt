package com.meal.planner.presentation.ui.startup

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
class StartupViewModel @Inject constructor(
    private val settingDataStore: SettingDataStore
) : ViewModel() {

    private val _uiState = MutableStateFlow(StartupUiState())
    val uiState: StateFlow<StartupUiState> = _uiState

    init {
        viewModelScope.launch {
            val weight = settingDataStore.getWeight()
            val dietType = settingDataStore.getDietType()
            _uiState.update {
                it.copy(
                    weight = weight?.toString() ?: "",
                    dietType = when (dietType) {
                        DietType.NULL -> DietType.BULKING
                        else -> dietType
                    }
                )
            }
        }
    }

    fun updateWeight(weight: String) {
        _uiState.update { it.copy(weight = weight) }
    }

    fun updateDietType(dietType: DietType) {
        _uiState.update { it.copy(dietType = dietType) }
    }

    fun saveWeight() {
        viewModelScope.launch {
            settingDataStore.updateWeight(_uiState.value.weight.toDouble())
        }
    }

    fun saveDietType() {
        viewModelScope.launch {
            settingDataStore.updateDietType(_uiState.value.dietType)
        }
    }
}