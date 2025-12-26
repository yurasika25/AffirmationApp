package com.affirmation.app.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf


val LocalBottomBarHideCount: ProvidableCompositionLocal<MutableIntState> =
    compositionLocalOf { error("LocalBottomBarHideCount is not provided") }


@Composable
fun HideBottomBar() {
    val hideCount = LocalBottomBarHideCount.current
    DisposableEffect(Unit) {
        hideCount.intValue += 1
        onDispose {
            hideCount.intValue -= 1
            if (hideCount.intValue < 0) hideCount.intValue = 0
        }
    }
}
