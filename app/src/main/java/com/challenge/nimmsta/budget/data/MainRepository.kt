package com.challenge.nimmsta.budget.data

import com.challenge.nimmsta.budget.data.model.BudgetData
import com.challenge.nimmsta.budget.data.model.toDomain
import com.challenge.nimmsta.budget.data.model.toEntity
import com.challenge.nimmsta.budget.storage.dao.BudgetDao
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MainRepository @Inject internal constructor(
    private val budgetDao: BudgetDao
) {
    fun getAllBudget() = budgetDao.getAllBudget().map {
        it.map { budget ->
            budget.toDomain()
        }
    }

    suspend fun insertBudget(budget: BudgetData) = budgetDao.insertBudget(
        budget.toEntity()
    )

    suspend fun deleteBudget(budget: BudgetData) = budgetDao.removeBudget(
        budget.toEntity()
    )
}