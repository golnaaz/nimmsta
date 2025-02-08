package com.challenge.nimmsta.budget.ui.viewmodel

import app.cash.turbine.test
import com.challenge.nimmsta.budget.data.MainRepository
import com.challenge.nimmsta.budget.data.model.BudgetData
import com.challenge.nimmsta.budget.storage.model.BudgetType
import com.challenge.nimmsta.budget.ui.viewmodel.model.MainAction
import com.challenge.nimmsta.budget.ui.viewmodel.model.MainEvent
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelShould {
    private val repository: MainRepository = mockk()
    private lateinit var viewModel: MainViewModel
    private val dispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)

        viewModel = MainViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun whenItemClicked_shouldOpenDialog() {
        runTest {
            viewModel.processAction(
                MainAction.OpenDialog
            )
            viewModel.state.test {
                expectMostRecentItem().apply {
                    showDialog shouldBe true
                }
            }
        }
    }

    @Test
    fun whenInit_ShouldReturnListOfItems() {
        runTest {
            val item = BudgetData(
                amount = 1000f,
                description = "Food",
                type = BudgetType.EXPENSE,
                id = 1,
                createdAt = 2000,
            )

            every { repository.getAllBudget() } returns flowOf(listOf(item))

            viewModel.processAction(
                MainAction.Init
            )
            viewModel.state.test {
                awaitItem().apply {
                    items shouldBe listOf(item)
                    totalExpense shouldBe 1000f
                    totalIncome shouldBe 0f
                }
            }
        }
    }

    @Test
    fun whenDeleteClicked_ShouldDeleteItem() {
        runTest {
            val item = BudgetData(
                amount = 1000f,
                description = "Food",
                type = BudgetType.EXPENSE,
                id = 1,
                createdAt = 2000,
            )

            coEvery { repository.deleteBudget(item) } returns Unit

            viewModel.processAction(
                MainAction.DeleteClick(
                    item
                )
            )
            viewModel.events.test {
                expectMostRecentItem() shouldBe MainEvent.DeleteClicked(
                    item
                )
            }
        }
    }

    @Test
    fun whenInsertClicked_givenItem_ShouldInsertItem() {
        runTest {
            val item = BudgetData(
                amount = 1000f,
                description = "Food",
                type = BudgetType.EXPENSE,
                id = 1,
                createdAt = 2000,
            )

            coEvery { repository.insertBudget(item) } returns 1

            viewModel.processAction(
                MainAction.InsertClick
            )
            viewModel.state.test {
                expectMostRecentItem().apply {
                    showDialog shouldBe false
                    amount shouldBe ""
                    description shouldBe ""
                    isExpense shouldBe false
                }
            }
        }
    }

    @Test
    fun whenCloseAction_shouldCloseDialog() {
        runTest {
            viewModel.processAction(
                MainAction.CloseDialog
            )
            viewModel.state.test {
                expectMostRecentItem().apply {
                    showDialog shouldBe false
                    amount shouldBe ""
                    description shouldBe ""
                    isExpense shouldBe false
                }
            }
        }
    }

}