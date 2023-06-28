package com.meal.planner.presentation.ui.food

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meal.planner.cache.database.DietDataSource
import com.meal.planner.model.Food
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class FoodViewModel @Inject constructor(
    private val dietDataSource: DietDataSource
) : ViewModel() {
    private val _uiState = MutableStateFlow(FoodUiState())
    val uiState: StateFlow<FoodUiState> = _uiState

    fun loadFood(dietId: String?, mealId: String?, foodId: String?) {
        viewModelScope.launch {
            if (dietId != null && mealId != null && foodId != null) {
                dietDataSource.dietFlow(dietId).collect { diet ->
                    if (diet != null) {
                        val food = diet
                            .meals
                            .first { meal -> meal.id == mealId }
                            .foods
                            .find { food -> food.id == foodId }
                        if (food != null) {
                            _uiState.update {
                                it.copy(
                                    id = food.id,
                                    name = food.name,
                                    protein = food.proteins.toString(),
                                    carb = food.carbs.toString(),
                                    fat = food.fats.toString(),
                                    quantity = food.quantity.toString()
                                )
                            }
                        }
                    }
                }
            }
        }
    }

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

    fun saveFood(dietId: String?, mealId: String?) {
        if (dietId != null && mealId != null) {
            viewModelScope.launch {
                val foodId = _uiState.value.id
                if (foodId != null) updateFood(dietId, mealId) else createFood(dietId, mealId)
            }
        }
    }

    private suspend fun updateFood(dietId: String, mealId: String) {
        val diet = dietDataSource.getDiet(dietId)
        if (diet != null) {
            diet.meals
                .first { it.id == mealId }
                .foods
                .first { it.id == _uiState.value.id }
                .apply {
                    name = _uiState.value.name
                    proteins = _uiState.value.protein.toDouble()
                    carbs = _uiState.value.carb.toDouble()
                    fats = _uiState.value.fat.toDouble()
                    quantity = _uiState.value.quantity.toDouble()
                }

            dietDataSource.updateDiets(listOf(diet))
        }
    }

    private suspend fun createFood(dietId: String, mealId: String) {
        val diet = dietDataSource.getDiet(dietId)
        if (diet != null) {
            diet.meals
                .first { it.id == mealId }
                .foods
                .add(
                    Food(
                        id = UUID.randomUUID().toString(),
                        name = _uiState.value.name,
                        proteins = _uiState.value.protein.toDouble(),
                        carbs = _uiState.value.carb.toDouble(),
                        fats = _uiState.value.fat.toDouble(),
                        quantity = _uiState.value.quantity.toDouble()
                    )
                )

            dietDataSource.updateDiets(listOf(diet))
        }
    }

    fun deleteFood(dietId: String?, mealId: String?) {
        if (dietId != null && mealId != null) {
            viewModelScope.launch {
                val diet = dietDataSource.getDiet(dietId)
                if (diet != null) {

                    diet.meals
                        .first { it.id == mealId }
                        .foods
                        .removeIf { it.id == _uiState.value.id }

                    dietDataSource.updateDiets(
                        listOf(diet)
                    )
                }
            }
        }
    }
}