package com.affirmation.app

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import com.affirmation.app.presentation.screen.TabsHostScreen
import com.affirmation.app.presentation.theme.AffirmationTheme

@Composable
fun AffirmationApp() {
    AffirmationTheme {
        Navigator(TabsHostScreen())
    }
}

