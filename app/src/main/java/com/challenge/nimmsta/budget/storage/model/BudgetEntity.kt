package com.challenge.nimmsta.budget.storage.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "budget")
data class BudgetEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val amount: Float,
    val description: String,
    val type: BudgetType,
    val createdAt: Long = System.currentTimeMillis(),
)