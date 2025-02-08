package com.challenge.nimmsta.budget.ui.viewmodel.model

import com.challenge.nimmsta.budget.data.model.BudgetData

data class MainViewState(
    val items: List<BudgetData> = emptyList(),
    val description: String = "",
    val amount: String = "",
    val totalIncome: Float = 0f,
    val totalExpense: Float = 0f,
    val isExpense: Boolean = false,
    val error: String = "",
    val isLoading: Boolean = false,
    val showDialog: Boolean = false,
)