package com.challenge.nimmsta.budget.ui.viewmodel.model

import com.challenge.nimmsta.budget.data.model.BudgetData


sealed interface MainAction {
    data object Init : MainAction
    data class OnAmountChange(val amount: String) : MainAction
    data class OnDescriptionChange(val des: String) : MainAction
    data class OnTypeChange(val isExpense: Boolean) : MainAction
    data object OpenDialog : MainAction
    data object CloseDialog : MainAction
    data object InsertClick : MainAction
    data class DeleteClick(val item: BudgetData) : MainAction
}