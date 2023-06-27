package com.meal.planner.cache.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface DietDao {

    @Insert
    suspend fun insertDiets(diets: List<DietEntity>): LongArray

    @Update
    suspend fun updateDiets(diets: List<DietEntity>): Int

    @Query("DELETE FROM diet_table WHERE id IN (:ids)")
    suspend fun deleteDiets(ids: List<String>): Int

    @Query("SELECT * FROM diet_table WHERE id = :id")
    fun dietFlow(id: String): Flow<DietEntity?>

    @Query("SELECT * FROM diet_table WHERE id = :id")
    suspend fun getDiet(id: String): DietEntity?

    @Query("SELECT * FROM diet_table")
    fun dietListFlow(): Flow<List<DietEntity>>

    @Query("SELECT * FROM diet_table")
    suspend fun getAllDiets(): List<DietEntity>
}