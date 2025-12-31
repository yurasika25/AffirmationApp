package com.affirmation.app.presentation.theme

import androidx.compose.runtime.staticCompositionLocalOf

val LocalAffirmationColors = staticCompositionLocalOf<AffirmationColors> {
    error("AffirmationColors not provided")
}