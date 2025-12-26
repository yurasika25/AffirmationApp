package com.affirmation.app

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import com.affirmation.app.presentation.ui.screens.MainScreen

@Composable
fun App() {
    MaterialTheme {
        Navigator(MainScreen())
    }
}

