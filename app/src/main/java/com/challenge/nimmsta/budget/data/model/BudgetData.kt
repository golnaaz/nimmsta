package com.challenge.nimmsta.budget.data.model

import com.challenge.nimmsta.budget.storage.model.BudgetType

data class BudgetData(
    val id: Long = 0,
    val amount: Float,
    val description: String,
    val type: BudgetType,
    val createdAt: Long,
)