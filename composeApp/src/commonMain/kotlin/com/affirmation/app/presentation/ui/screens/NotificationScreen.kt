package com.affirmation.app.presentation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import com.affirmation.app.createHttpClient
import com.affirmation.app.data.network.ApiService
import com.affirmation.app.domain.model.NotificationData
import com.affirmation.app.presentation.viewModel.NotificationsViewModel
import com.affirmation.app.utils.GlobalTopBar

class NotificationScreen : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {

        val apiService = remember { ApiService(createHttpClient()) }
        val viewModel = remember { NotificationsViewModel(apiService) }

        val data by remember { derivedStateOf { viewModel.data } }
        val isLoading by remember { derivedStateOf { viewModel.isLoading } }
        val errorMessage by remember { derivedStateOf { viewModel.errorMessage } }

        LaunchedEffect(Unit) {
            viewModel.loadData()
        }

        Scaffold(
            topBar = { GlobalTopBar("Favorites") },
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                when {
                    isLoading -> CircularProgressIndicator()
                    errorMessage != null -> {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Error: $errorMessage", color = Color.Red)
                            Spacer(Modifier.height(8.dp))
                            Button(onClick = { viewModel.loadData() }) {
                                Text("Retry")
                            }
                        }
                    }

                    data.isEmpty() -> Text("No favourites yet", color = Color.Gray)
                    else -> LazyColumn(
                        contentPadding = PaddingValues(
                            top = 16.dp,
                            bottom = 40.dp
                        ),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(data) { notification ->
                            NotificationCard(notification)
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun NotificationCard(notification: NotificationData) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(12.dp)),
            elevation = CardDefaults.cardElevation(4.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = notification.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = notification.message,
                    fontSize = 15.sp,
                    color = Color(0xFF555555)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = notification.dateText,
                    fontSize = 13.sp,
                    color = Color.Gray
                )
            }
        }
    }
}
