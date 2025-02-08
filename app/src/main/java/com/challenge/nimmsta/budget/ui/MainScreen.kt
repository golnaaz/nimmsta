package com.challenge.nimmsta.budget.ui

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.challenge.nimmsta.budget.ui.viewmodel.MainViewModel
import com.challenge.nimmsta.budget.ui.viewmodel.model.MainAction
import com.challenge.nimmsta.budget.ui.viewmodel.model.MainEvent
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@SuppressLint("RememberReturnType")
@Composable
fun MainScreen(
    navHostController: NavHostController,
    viewModel: MainViewModel = hiltViewModel()
) {
    val state by remember { viewModel.state }.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(viewModel) {
        viewModel.processAction(MainAction.Init)
        viewModel.events.onEach {
            when (it) {
                is MainEvent.DeleteClicked -> Toast.makeText(
                    context,
                    "${it.item.description} deleted successfully",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }.launchIn(this)
    }

    BudgetContent(
        state = state,
        onAction = viewModel::processAction,
    )
}