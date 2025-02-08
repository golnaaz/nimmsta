package com.challenge.nimmsta

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.challenge.nimmsta.budget.ui.navigation.AppNavHost
import com.challenge.nimmsta.ui.theme.NimmstaTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NimmstaTheme {
                AppNavHost()
            }
        }
    }
}