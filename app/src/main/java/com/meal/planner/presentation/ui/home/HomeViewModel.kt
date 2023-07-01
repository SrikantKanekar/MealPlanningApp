package com.meal.planner.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meal.planner.cache.dataStore.SettingDataStore
import com.meal.planner.cache.database.DietDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dietDataSource: DietDataSource,
    private val settingDataStore: SettingDataStore
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        viewModelScope.launch {
            settingDataStore.dietTypeFlow.collect { dietType ->
                _uiState.update { it.copy(dietType = dietType) }
            }
        }
        viewModelScope.launch {
            settingDataStore.weightFlow.collect { weight ->
                _uiState.update { it.copy(weight = weight) }
            }
        }
        viewModelScope.launch {
            dietDataSource.dietListFlow().collect { diets ->
                _uiState.update { it.copy(diets = diets) }
            }
        }
    }
}