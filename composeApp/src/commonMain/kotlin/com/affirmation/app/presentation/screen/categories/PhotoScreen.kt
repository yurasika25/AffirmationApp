package com.affirmation.app.presentation.screen.categories

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen

class PhotoScreen : Screen {
    @Composable
    override fun Content() {
        SimpleCategoryScaffold(title = "Photos")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SimpleCategoryScaffold(title: String) {
    Scaffold(
        topBar = { TopAppBar(title = { Text(title) }) }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Selected category: $title",
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

