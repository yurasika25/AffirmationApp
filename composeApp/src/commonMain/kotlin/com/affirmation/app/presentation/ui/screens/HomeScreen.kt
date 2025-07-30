package com.affirmation.app.presentation.ui.screens

import affirmationapp.composeapp.generated.resources.Res
import affirmationapp.composeapp.generated.resources.im_me
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.affirmation.app.utils.items
import com.affirmation.app.utils.monthNames
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

class HomeScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        var showLogoutDialog by remember { mutableStateOf(false) }

        val todayFormatted = remember {
            val currentDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            val monthName = monthNames[currentDateTime.monthNumber - 1]
            "$monthName ${currentDateTime.dayOfMonth}, ${currentDateTime.year}"
        }

        Scaffold(
            topBar = {
                HomeTopBar(
                    username = "Yurii",
                    date = todayFormatted,
                    onAvatarClick = { showLogoutDialog = true }
                )
            }
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(items) { item ->
                    HomeItemCard(
                        icon = item.icon,
                        title = item.text,
                        onClick = {
                            navigator.push(
                                DetailScreen(
                                    icon = item.icon,
                                    title = item.text,
                                    subtitle = item.subtitle
                                )
                            )
                        }
                    )
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
}

@Composable
private fun HomeTopBar(
    username: String,
    date: String,
    onAvatarClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .statusBarsPadding()
    ) {
        Column {
            Text(
                text = "Hi, $username!",
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
            Text(
                text = "Today is $date",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Card(
            shape = CircleShape,
            colors = CardDefaults.cardColors(containerColor = Color.Gray),
            elevation = CardDefaults.cardElevation(0.dp),
            modifier = Modifier
                .size(63.dp)
                .clickable { onAvatarClick() }
        ) {
            Image(
                painter = painterResource(Res.drawable.im_me),
                contentDescription = "Avatar",
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
private fun HomeItemCard(
    icon: Any,
    title: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(34f)
    ) {
        Box(
            modifier = Modifier
                .height(120.dp)
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(icon as DrawableResource),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Text(
                text = title,
                color = Color.White,
                fontSize = 20.sp,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(8.dp)
            )
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
