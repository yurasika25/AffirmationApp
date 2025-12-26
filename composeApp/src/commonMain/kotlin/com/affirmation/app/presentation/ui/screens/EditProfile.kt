package com.affirmation.app.presentation.ui.screens

import AffirmationToolBar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.affirmation.app.createHttpClient
import com.affirmation.app.data.network.ApiService
import com.affirmation.app.domain.model.UpdateUserProfileModel
import com.affirmation.app.utils.HideBottomBar
import kotlinx.coroutines.launch

data class EditProfileScreen(
    val firstNameArg: String,
    val lastNameArg: String,
    val ageArg: String,
    val phoneArg: String,
    val emailArg: String,
    val genderArg: String
) : Screen {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {

        HideBottomBar()

        val pageBgTop = Color(0xFFF7FAFF)
        val pageBgBottom = Color(0xFFEAF1FF)

        val navigator = LocalNavigator.currentOrThrow
        val coroutineScope = rememberCoroutineScope()
        val apiService = remember { ApiService(createHttpClient()) }

        var firstName by remember { mutableStateOf(firstNameArg) }
        var lastName by remember { mutableStateOf(lastNameArg) }
        var age by remember { mutableStateOf(ageArg) }
        var phone by remember { mutableStateOf(phoneArg) }
        var email by remember { mutableStateOf(emailArg) }
        var gender by remember { mutableStateOf(genderArg) }

        var isLoading by remember { mutableStateOf(false) }
        var message by remember { mutableStateOf<String?>(null) }

        Scaffold(
            topBar = {
                AffirmationToolBar(
                    title = "Details",
                    showBack = true,
                    onBackClick = { navigator.pop() }
                )
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Brush.verticalGradient(listOf(pageBgTop, pageBgBottom)))
                    .padding(padding)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                OutlinedTextField(
                    value = firstName,
                    onValueChange = { firstName = it },
                    label = { Text("First Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(12.dp))

                OutlinedTextField(
                    value = lastName,
                    onValueChange = { lastName = it },
                    label = { Text("Last Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(12.dp))

                OutlinedTextField(
                    value = age,
                    onValueChange = { age = it },
                    label = { Text("Age") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(12.dp))

                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("Phone") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(12.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(12.dp))

                OutlinedTextField(
                    value = gender,
                    onValueChange = { gender = it },
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
                                    firstName = firstName,
                                    lastName = lastName,
                                    age = age,
                                    phoneNumber = phone,
                                    email = email,
                                    gender = gender
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
