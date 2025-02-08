package com.challenge.nimmsta.budget.storage.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.challenge.nimmsta.budget.storage.model.BudgetEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BudgetDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBudget(budgetEntity: BudgetEntity): Long

    @Delete
    suspend fun removeBudget(item: BudgetEntity)

    @Query("SELECT * FROM budget")
    fun getAllBudget(): Flow<List<BudgetEntity>>
}