package com.affirmation.app.presentation.screen.categories

import AffirmationToolBar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.affirmation.app.presentation.theme.LocalAffirmationColors

class VideoScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val colors = LocalAffirmationColors.current


        Scaffold(
            topBar = {
                AffirmationToolBar(
                    title = "Videos",
                    showBack = true,
                    onBackClick = { navigator.pop() }
                )
            }
        ) { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                colors.screenGradientTop,
                                colors.screenGradientBottom
                            )
                        )
                    ),

                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "Selected category: Videos",
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}

