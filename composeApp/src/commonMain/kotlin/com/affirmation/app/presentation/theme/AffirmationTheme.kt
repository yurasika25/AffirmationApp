package com.affirmation.app.presentation.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun AffirmationTheme(
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalAffirmationColors provides LightAffirmationColors
    ) {
        content()
    }
}