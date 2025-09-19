package com.affirmation.app.presentation.ui.screens

import affirmationapp.composeapp.generated.resources.Res
import affirmationapp.composeapp.generated.resources.arrow_back
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.affirmation.app.createHttpClient
import com.affirmation.app.data.network.ApiService
import com.affirmation.app.domain.model.UpdateUserProfileModel
import com.affirmation.app.domain.model.UserProfileModel
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource

class EditProfileScreen(private val currentProfile: UserProfileModel?) : Screen {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow
        val coroutineScope = rememberCoroutineScope()
        val apiService = remember { ApiService(createHttpClient()) }

        var firstName by remember { mutableStateOf(currentProfile?.firstName) }
        var lastName by remember { mutableStateOf(currentProfile?.lastName) }
        var age by remember { mutableStateOf(currentProfile?.age) }
        var phone by remember { mutableStateOf(currentProfile?.phoneNumber) }
        var email by remember { mutableStateOf(currentProfile?.email) }
        var gender by remember { mutableStateOf(currentProfile?.gender) }

        var isLoading by remember { mutableStateOf(false) }
        var message by remember { mutableStateOf<String?>(null) }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Details") },
                    navigationIcon = {
                        Icon(
                            painter = painterResource(Res.drawable.arrow_back),
                            contentDescription = "Arrow back icon",
                            tint = Color(0xFF9985D0),
                            modifier = Modifier
                                .size(32.dp)
                                .clickable {
                                    navigator.pop()
                                }
                        )
                    }
                )
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFFDF7FF))
                    .padding(padding)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = firstName!!,
                    onValueChange = { firstName = it },
                    label = { Text("First Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(12.dp))
                OutlinedTextField(
                    value = lastName ?: "My Name",
                    onValueChange = { lastName = it },
                    label = { Text("Last Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(12.dp))
                OutlinedTextField(
                    value = age ?: "25",
                    onValueChange = { age = it },
                    label = { Text("Age") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(12.dp))
                OutlinedTextField(
                    value = phone ?: "+123456789",
                    onValueChange = { phone = it },
                    label = { Text("Phone") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(12.dp))
                OutlinedTextField(
                    value = email ?: "test@example.com",
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(12.dp))
                OutlinedTextField(
                    value = gender ?: "None",
                    onValueChange = { email = it },
                    label = { Text("Gender") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(24.dp))
                Button(
                    onClick = {
                        isLoading = true
                        message = null
                        coroutineScope.launch {
                            val success = apiService.updateUserProfile(
                                UpdateUserProfileModel(
                                    firstName = firstName!!,
                                    lastName = lastName!!,
                                    age = age!!,
                                    phoneNumber = phone!!,
                                    email = email!!,
                                    gender = gender!!
                                )
                            )
                            isLoading = false
                            message = if (success) "Profile updated successfully" else "Failed to update profile"
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    enabled = !isLoading
                ) {
                    if (isLoading) CircularProgressIndicator(modifier = Modifier.size(20.dp))
                    else Text("Save Changes", fontWeight = FontWeight.Bold)
                }

                message?.let {
                    Spacer(Modifier.height(12.dp))
                    Text(
                        text = it,
                        color = if (it.contains("Failed")) MaterialTheme.colorScheme.error else Color.Green,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}
