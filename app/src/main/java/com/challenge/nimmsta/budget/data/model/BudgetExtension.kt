package com.challenge.nimmsta.budget.data.model

import com.challenge.nimmsta.budget.storage.model.BudgetEntity

fun BudgetEntity.toDomain() = BudgetData(
    amount = this.amount,
    description = this.description,
    type = this.type,
    id = this.id,
    createdAt = this.createdAt,
)

fun BudgetData.toEntity() = BudgetEntity(
    amount = this.amount,
    description = this.description,
    type = this.type,
    id = this.id,
    createdAt = this.createdAt,
)