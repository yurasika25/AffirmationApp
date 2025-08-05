package com.affirmation.app.presentation.ui.screens

import affirmationapp.composeapp.generated.resources.Res
import affirmationapp.composeapp.generated.resources.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.affirmation.app.data.network.ApiService
import com.affirmation.app.createHttpClient
import com.affirmation.app.presentation.viewModel.FavoriteViewModel
import com.affirmation.app.utils.GlobalTopBar
import kotlin.random.Random
import org.jetbrains.compose.resources.painterResource

class FavoriteScreen : Screen {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val apiService = remember { ApiService(createHttpClient()) }
        val viewModel = remember { FavoriteViewModel(apiService) }

        val data by remember { derivedStateOf { viewModel.data } }
        val isLoading by remember { derivedStateOf { viewModel.isLoading } }
        val errorMessage by remember { derivedStateOf { viewModel.errorMessage } }

        val images = listOf(
            painterResource(Res.drawable.image1),
            painterResource(Res.drawable.image2),
            painterResource(Res.drawable.image3),
            painterResource(Res.drawable.image4),
            painterResource(Res.drawable.image5),
            painterResource(Res.drawable.image6),
            painterResource(Res.drawable.image7),
            painterResource(Res.drawable.image8),
            painterResource(Res.drawable.image10)
        )

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
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp),
                        contentPadding = PaddingValues(
                            top = 16.dp,
                            bottom = 50.dp
                        ),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(data) { item ->
                            val randomImage = remember { images[Random.nextInt(images.size)] }
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                elevation = CardDefaults.cardElevation(0.dp),
                                shape = RoundedCornerShape(18.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White)
                            ) {
                                Column(
                                    modifier = Modifier.fillMaxWidth()
                                ) {

                                    Card(
                                        modifier = Modifier
                                            .padding(16.dp)
                                            .height(240.dp)
                                            .fillMaxWidth(),
                                        elevation = CardDefaults.cardElevation(0.dp),
                                        shape = RoundedCornerShape(12.dp)
                                    ) {
                                        Image(
                                            painter = randomImage,
                                            contentDescription = null,
                                            modifier = Modifier.fillMaxSize(),
                                            contentScale = ContentScale.Crop
                                        )

                                    }
                                    Text(
                                        text = item.text,
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = Color.Black,
                                        modifier = Modifier
                                            .padding(16.dp)
                                            .align(Alignment.Start)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
