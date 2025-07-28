package com.affirmation.app

import com.affirmation.app.ui.MainScreen
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition

@Composable
fun App() {
    MaterialTheme {
        Navigator(MainScreen()) { navigator ->
            SlideTransition(navigator)
        }
    }
}
