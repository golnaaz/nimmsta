package com.challenge.nimmsta.budget.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.challenge.nimmsta.budget.ui.viewmodel.model.MainAction
import com.challenge.nimmsta.budget.ui.viewmodel.model.MainViewState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBudgetDialog(
    state: MainViewState,
    onAction: (MainAction) -> Unit,
) {
    BasicAlertDialog(
        onDismissRequest = {
            onAction(MainAction.CloseDialog)
        }
    ) {
        Surface(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight(),
            shape = MaterialTheme.shapes.large
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                OutlinedTextField(
                    value = state.description,
                    onValueChange = {
                        onAction(MainAction.OnDescriptionChange(it))
                    },
                    label = { Text("Description") },
                    placeholder = { Text("Traveling...") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth(),
                    isError = state.description.isEmpty(),
                    maxLines = 1,
                )
                OutlinedTextField(
                    value = state.amount,
                    onValueChange = { onAction(MainAction.OnAmountChange(it)) },
                    label = { Text("Amount") },
                    placeholder = { Text("1200") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth(),
                    isError = state.amount.isEmpty(),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = if (state.isExpense) "Expense" else "Income",
                        modifier = Modifier.padding(end = 8.dp),
                        fontSize = 14.sp,
                    )
                    Switch(
                        checked = state.isExpense,
                        onCheckedChange = { onAction(MainAction.OnTypeChange(it)) },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = Color(0xFF9A0033),
                            uncheckedThumbColor = Color.LightGray,
                            uncheckedTrackColor = Color(0xFF067206)
                        )
                    )
                }

                Button(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClick = { onAction(MainAction.InsertClick) }) {
                    Text("Insert")
                }
            }
        }
    }
}