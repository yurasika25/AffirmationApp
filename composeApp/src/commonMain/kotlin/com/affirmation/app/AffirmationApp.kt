package com.affirmation.app

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import com.affirmation.app.presentation.screen.MainScreen

@Composable
fun AffirmationApp() {
    MaterialTheme {
        Navigator(MainScreen())
    }
}

