package com.affirmation.app.presentation.ui.screens

import affirmationapp.composeapp.generated.resources.Res
import affirmationapp.composeapp.generated.resources.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.affirmation.app.data.network.ApiService
import com.affirmation.app.createHttpClient
import com.affirmation.app.domain.model.UpdateUserProfileModel
import com.affirmation.app.domain.model.UserProfileModel
import com.affirmation.app.utils.GlobalTopBar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

class ProfileScreen : Screen {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val coroutineScope = rememberCoroutineScope()
        val apiService = remember { ApiService(createHttpClient()) }
        var userProfileModel by remember { mutableStateOf<UserProfileModel?>(null) }
        var error by remember { mutableStateOf<String?>(null) }
        var showLogoutDialog by remember { mutableStateOf(false) }


        var newName by remember { mutableStateOf("") }
        var isLoading by remember { mutableStateOf(false) }
        var updateMessage by remember { mutableStateOf<String?>(null) }

        LaunchedEffect(Unit) {
            val profile = loadUserProfile(apiService)
            getUserDetails(apiService)
            if (profile != null) userProfileModel = profile else error = "Failed to load profile"
        }

        val backgroundColor = Color(0xFFFDF7FF)

        Scaffold(
            topBar = { GlobalTopBar("My Profile") },
        ) { padding ->
            when {
                error != null -> Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = error!!,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                userProfileModel == null -> Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }

                else -> LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(backgroundColor)
                        .padding(padding)
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 24.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(24.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Image(
                                    painter = painterResource(Res.drawable.im_me),
                                    contentDescription = "Avatar",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(120.dp)
                                        .clip(CircleShape)
                                        .background(Color.Gray)
                                        .clickable { /* TODO: Change avatar */ }
                                )

                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    text = "${userProfileModel!!.firstName} ${userProfileModel!!.lastName}",
                                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
                                )

                                Spacer(modifier = Modifier.height(8.dp))
                                ProfileDetailRow(
                                    label = "Age",
                                    value = userProfileModel!!.age.toString()
                                )
                                ProfileDetailRow(
                                    label = "Gender",
                                    value = userProfileModel!!.gender
                                )
                                ProfileDetailRow(
                                    label = "Phone",
                                    value = userProfileModel!!.phoneNumber
                                )
                                ProfileDetailRow(label = "Email", value = userProfileModel!!.email)

                                Spacer(modifier = Modifier.height(16.dp))

                                OutlinedButton(
                                    onClick = { showLogoutDialog = true },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 8.dp)
                                ) { Text("Logout") }
                            }
                        }

                        Button(
                            onClick = {
                                navigator.push(EditProfileScreen(userProfileModel))
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) { Text("Edit Profile") }

                        OutlinedButton(
                            onClick = { showLogoutDialog = true },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp)
                        ) { Text("Logout") }
                    }

                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 24.dp),
                            shape = RoundedCornerShape(24.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(24.dp)
                            ) {
                                Text(
                                    "Settings",
                                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Medium),
                                    modifier = Modifier.padding(bottom = 16.dp)
                                )

                                SettingItem(
                                    iconRes = Res.drawable.im_night_mode,
                                    label = "Dark Mode",
                                    description = "Enable dark theme"
                                )

                                SettingItem(
                                    iconRes = Res.drawable.im_notifications,
                                    label = "Notifications",
                                    description = "Enable app notifications"
                                )

                                SettingItem(
                                    iconRes = Res.drawable.im_language,
                                    label = "Language",
                                    description = "Change app language"
                                )

                                SettingItem(
                                    iconRes = Res.drawable.im_help,
                                    label = "Help & Support",
                                    description = "Get help or contact us"
                                )

                                SettingItem(
                                    iconRes = Res.drawable.im_privacy,
                                    label = "Privacy Policy",
                                    description = "Read our privacy terms"
                                )

                                SettingItem(
                                    iconRes = Res.drawable.im_feedback,
                                    label = "Send Feedback",
                                    description = "Share your thoughts with us"
                                )
                                SettingItem(
                                    iconRes = Res.drawable.im_about,
                                    label = "About",
                                    description = "Version 1.0.0"
                                )
                            }
                        }
                    }
                }
            }
        }
        if (showLogoutDialog) {
            LogoutDialog(
                onConfirm = { showLogoutDialog = false /* handle logout */ },
                onDismiss = { showLogoutDialog = false }
            )
        }
    }

    @Composable
    private fun ProfileDetailRow(label: String, value: String) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }

    @Composable
    private fun SettingItem(iconRes: DrawableResource, label: String, description: String) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 4.dp)
        ) {
            Image(
                painter = painterResource(iconRes),
                contentDescription = label,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = label, style = MaterialTheme.typography.bodyLarge)
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }

    private suspend fun loadUserProfile(apiService: ApiService): UserProfileModel? {
        return try {
            withContext(Dispatchers.IO) { apiService.getUserProfile() }
        } catch (e: Exception) {
            null
        }
    }

    private suspend fun getUserDetails(apiService: ApiService): UpdateUserProfileModel? {
        return try {
            withContext(Dispatchers.IO) { apiService.getUserDetails() }
        } catch (e: Exception) {
            null
        }
    }
}

@Composable
private fun LogoutDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Confirm Logout") },
        text = { Text("Are you sure you want to log out?") },
        confirmButton = {
            TextButton(onClick = onConfirm) { Text("Log out") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}

