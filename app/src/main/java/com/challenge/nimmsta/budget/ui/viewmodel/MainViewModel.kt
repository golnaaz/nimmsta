package com.challenge.nimmsta.budget.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.challenge.nimmsta.budget.data.MainRepository
import com.challenge.nimmsta.budget.data.model.BudgetData
import com.challenge.nimmsta.budget.storage.model.BudgetType
import com.challenge.nimmsta.budget.ui.viewmodel.model.MainAction
import com.challenge.nimmsta.budget.ui.viewmodel.model.MainEvent
import com.challenge.nimmsta.budget.ui.viewmodel.model.MainViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository,
) : ViewModel() {

    private val _events = Channel<MainEvent>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    private val _state = MutableStateFlow(MainViewState())
    val state: StateFlow<MainViewState> = _state.asStateFlow()

    fun processAction(action: MainAction) {
        when (action) {
            MainAction.Init -> {
                getMainData()
            }

            is MainAction.DeleteClick -> {
                deleteItem(action.item)
            }

            is MainAction.InsertClick -> {
                addItem()
            }

            MainAction.OpenDialog -> {
                _state.update {
                    it.copy(
                        showDialog = true
                    )
                }
            }

            MainAction.CloseDialog -> closeDialog()
            is MainAction.OnAmountChange -> _state.update {
                it.copy(
                    amount = action.amount
                )
            }

            is MainAction.OnDescriptionChange -> _state.update {
                it.copy(
                    description = action.des
                )
            }

            is MainAction.OnTypeChange -> _state.update {
                it.copy(
                    isExpense = action.isExpense
                )
            }
        }
    }

    private fun closeDialog() {
        _state.update {
            it.copy(
                showDialog = false,
                amount = "",
                description = "",
                isExpense = false,
            )
        }

    }

    private fun addItem() {
        if (state.value.amount.isEmpty() || state.value.description.isEmpty()) return

        viewModelScope.launch {
            repository.insertBudget(
                BudgetData(
                    amount = state.value.amount.toFloat(),
                    description = state.value.description,
                    type = if (state.value.isExpense) BudgetType.EXPENSE else BudgetType.INCOME,
                    createdAt = System.currentTimeMillis(),
                )
            )
        }

        closeDialog()

    }

    private fun deleteItem(item: BudgetData) {
        viewModelScope.launch {
            repository.deleteBudget(item)
            _events.trySend(MainEvent.DeleteClicked(item))
        }
    }

    private fun updatePieChart() {
        val totalIncome = state.value.items.filter { it.type == BudgetType.INCOME }
            .sumOf { it.amount.toBigDecimal() }

        val totalExpense = state.value.items.filter { it.type == BudgetType.EXPENSE }
            .sumOf { it.amount.toBigDecimal() }

        _state.update {
            it.copy(
                totalExpense = totalExpense.toFloat(),
                totalIncome = totalIncome.toFloat()
            )
        }
    }

    private fun getMainData() {
        viewModelScope.launch {
            repository.getAllBudget().collect { result ->
                _state.update {
                    it.copy(
                        items = result
                    )
                }
                updatePieChart()
            }
        }
    }
}