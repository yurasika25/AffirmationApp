package com.affirmation.app.presentation.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.affirmation.app.data.network.ApiService
import com.affirmation.app.createHttpClient
import com.affirmation.app.presentation.viewModel.FavoriteViewModel

class FavoriteScreen : Screen {

    @Composable
    override fun Content() {

        val apiService = remember { ApiService(createHttpClient()) }
        val viewModel = remember { FavoriteViewModel(apiService) }

        val data by remember { derivedStateOf { viewModel.data } }
        val isLoading by remember { derivedStateOf { viewModel.isLoading } }
        val errorMessage by remember { derivedStateOf { viewModel.errorMessage } }

        LaunchedEffect(Unit) {
            viewModel.loadData()
        }

        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            when {
                isLoading -> Text("Loading...")
                errorMessage != null -> {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Error: $errorMessage", color = Color.Red)
                        Button(onClick = { viewModel.loadData() }) {
                            Text("Retry")
                        }
                    }
                }
                data.isEmpty() -> Text("No data available")
                else -> LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(data) { item ->
                        Text(
                            text = item.text,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            color = Color.Black
                        )
                        HorizontalDivider(
                            Modifier,
                            DividerDefaults.Thickness,
                            DividerDefaults.color
                        )
                    }
                }
            }
        }
    }
}
