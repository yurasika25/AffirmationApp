package com.affirmation.app

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideOrientation
import cafe.adriel.voyager.transitions.SlideTransition
import com.affirmation.app.presentation.nav.TabsHostScreen
import com.affirmation.app.presentation.theme.AffirmationTheme

@Composable
fun AffirmationApp() {
    AffirmationTheme {
        Navigator(TabsHostScreen()) { navigator ->
            SlideTransition(
                navigator = navigator,
                orientation = SlideOrientation.Horizontal
            )
        }
    }
}

