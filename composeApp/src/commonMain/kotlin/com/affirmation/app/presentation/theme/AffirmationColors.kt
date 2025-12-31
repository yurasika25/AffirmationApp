package com.affirmation.app.presentation.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class AffirmationColors(
    val screenGradientTop: Color,
    val screenGradientBottom: Color,
    val toolbarBackground: Color,
    val primaryTextColor: Color,
    val secondaryTextColor: Color,
    val borderColor: Color,
    val loaderColor: Color
)

val LightAffirmationColors = AffirmationColors(
    screenGradientTop = Blue50,
    screenGradientBottom = Blue100,
    toolbarBackground = Blue50,
    primaryTextColor = Gray900,
    secondaryTextColor = Gray200,
    borderColor = Gray1000,
    loaderColor = Blue700
)