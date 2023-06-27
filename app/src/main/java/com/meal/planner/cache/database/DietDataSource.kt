package com.meal.planner.cache.database

import com.meal.planner.model.Diet
import com.meal.planner.util.cacheCall
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DietDataSource @Inject constructor(
    private val dietDao: DietDao,
) {

    suspend fun insertDiets(diets: List<Diet>) {
        when {
            diets.isNotEmpty() -> {
                cacheCall(IO) {
                    dietDao.insertDiets(
                        diets.map { diet ->
                            dietEntityMapper(diet)
                        }
                    )
                }
            }
        }
    }

    suspend fun updateDiets(diets: List<Diet>) {
        when {
            diets.isNotEmpty() -> {
                cacheCall(IO) {
                    dietDao.updateDiets(
                        diets.map { diet ->
                            dietEntityMapper(diet)
                        }
                    )
                }
            }
        }
    }

    suspend fun deleteDiets(ids: List<String>) {
        when {
            ids.isNotEmpty() -> {
                cacheCall(IO) {
                    dietDao.deleteDiets(ids)
                }
            }
        }
    }

    fun dietFlow(id: String): Flow<Diet?> {
        return dietDao.dietFlow(id).map { dietEntity ->
            when (dietEntity) {
                null -> null
                else -> dietMapper(dietEntity)
            }
        }
    }

    suspend fun getDiet(id: String): Diet? {
        return cacheCall(IO) {
            dietDao.getDiet(id)?.let { dietEntity ->
                dietMapper(dietEntity)
            }
        }
    }

    fun dietListFlow(): Flow<List<Diet>> {
        return dietDao.dietListFlow().map { entities ->
            entities.map { dietMapper(it) }
        }
    }

    suspend fun getAllDiets(): List<Diet>? {
        return cacheCall(IO) {
            dietDao.getAllDiets().map { dietEntity ->
                dietMapper(dietEntity)
            }
        }
    }
}