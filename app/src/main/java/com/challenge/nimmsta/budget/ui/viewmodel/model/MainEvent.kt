package com.challenge.nimmsta.budget.ui.viewmodel.model

import com.challenge.nimmsta.budget.data.model.BudgetData


sealed interface MainEvent {
    data class DeleteClicked(val item: BudgetData) : MainEvent
}