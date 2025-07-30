package com.affirmation.app.presentation.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import com.affirmation.app.data.network.ApiService
import com.affirmation.app.createHttpClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class ProfileScreen : Screen {

    @Composable
    override fun Content() {
        val apiService = remember { ApiService(createHttpClient()) }
        var profileData by remember { mutableStateOf("Loading...") }

        LaunchedEffect(Unit) {
            profileData = loadUserProfile(apiService)
        }

        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(profileData)
        }
    }

    private suspend fun loadUserProfile(apiService: ApiService): String {
        return try {
            withContext(Dispatchers.IO) {
                apiService.getUserProfile()
            }
        } catch (e: Exception) {
            "Error: ${e.message}"
        }
    }
}
