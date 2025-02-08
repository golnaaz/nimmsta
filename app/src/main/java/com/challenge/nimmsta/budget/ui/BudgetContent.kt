package com.challenge.nimmsta.budget.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.challenge.nimmsta.R
import com.challenge.nimmsta.budget.data.model.BudgetData
import com.challenge.nimmsta.budget.storage.model.BudgetType
import com.challenge.nimmsta.budget.ui.component.PieChart
import com.challenge.nimmsta.budget.ui.component.PieChartData
import com.challenge.nimmsta.budget.ui.viewmodel.model.MainAction
import com.challenge.nimmsta.budget.ui.viewmodel.model.MainViewState
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetContent(
    state: MainViewState,
    onAction: (MainAction) -> Unit,
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onAction(MainAction.OpenDialog) },
                containerColor = Color.Blue,
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Track", color = Color.White)
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black)
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier.padding(paddingValues)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .padding(16.dp)
                        .weight(1f)
                ) {
                    items(state.items) { item ->
                        BudgetItem(item, onAction)
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                ) {
                    PieChartScreen(state.totalIncome, state.totalExpense)
                }
                Text(
                    text = state.error,
                    color = Color.Black,
                )
            }
        }
    )

    if (state.showDialog) {
        AddBudgetDialog(
            state,
            onAction
        )
    }
}

@Composable
fun PieChartScreen(income: Float, expense: Float) {
    val balance = income - expense
    val slices = listOf(
        PieChartData(value = income, color = Color(0xFF067206), label = "Income"),
        PieChartData(value = expense, color = Color(0xFF9A0033), label = "Expense"),
        PieChartData(value = balance, color = Color(0xFFD0BCFF), label = "Balance")
    )

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = Modifier.padding(5.dp),
            text = "Income:$income  Expenses:$expense  Balances:$balance"
        )
        Spacer(modifier = Modifier.height(8.dp))

        PieChart(
            slices = slices,
            modifier = Modifier.size(200.dp)
        )
    }
}

@Composable
fun BudgetItem(item: BudgetData, onAction: (MainAction) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(6.dp)
            ),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(16.dp)
                .clickable {
                    onAction(MainAction.DeleteClick(item))
                },
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(modifier = Modifier.weight(1f)) {
                Text(text = item.description, color = Color.DarkGray, fontSize = 18.sp)
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = item.amount.toString(),
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontSize = 20.sp,
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = formatTime(item.createdAt), color = Color.DarkGray, fontSize = 12.sp)
            }
            Image(
                painter =
                when (item.type) {
                    BudgetType.INCOME -> painterResource(id = R.drawable.ic_income)
                    BudgetType.EXPENSE -> painterResource(id = R.drawable.ic_expenses)
                },
                contentDescription = "Type",
                modifier = Modifier.clickable {
                    onAction(MainAction.DeleteClick(item))
                }
            )
        }
    }
}

fun formatTime(timestamp: Long): String {
    val sdf =
        SimpleDateFormat("dd MMM yyyy", Locale.getDefault())  // Format: 07 Feb 2025, 14:30
    return sdf.format(Date(timestamp))
}

@Preview
@Composable
fun BudgetContentPreview() {
    BudgetContent(
        state = MainViewState(
            items = listOf(
                BudgetData(
                    amount = 1000f,
                    description = "Food",
                    type = BudgetType.EXPENSE,
                    id = 0,
                    createdAt = 2000,
                )
            ),
        )
    ) { }
}
