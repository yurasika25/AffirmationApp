package com.affirmation.app.presentation.screen.main

import AffirmationToolBar
import affirmationapp.composeapp.generated.resources.Res
import affirmationapp.composeapp.generated.resources.play_filled
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.affirmation.app.createHttpClient
import com.affirmation.app.data.network.ApiService
import com.affirmation.app.domain.model.NotificationModel
import com.affirmation.app.presentation.screen.PlayerScreen
import com.affirmation.app.presentation.viewModel.NotificationsViewModel
import com.affirmation.app.utils.items
import org.jetbrains.compose.resources.painterResource
import kotlin.random.Random

class NotificationScreen() : Screen {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val apiService = remember { ApiService(createHttpClient()) }
        val viewModel = remember { NotificationsViewModel(apiService) }

        val serverData by remember { derivedStateOf { viewModel.data } }
        val isLoading by remember { derivedStateOf { viewModel.isLoading } }
        val errorMessage by remember { derivedStateOf { viewModel.errorMessage } }

        val localData by remember {
            mutableStateOf(fakeNotifications(count = 8))
        }

        val navigator = LocalNavigator.currentOrThrow

        val dataToShow = serverData.ifEmpty { localData }

//        LaunchedEffect(Unit) {
//            viewModel.loadData() // harmless; UI will show local data until real data arrives
//        }

        val pageBg = Color(0xFFFAF7FF)

        Scaffold(
            containerColor = pageBg,
            topBar = { AffirmationToolBar("Notifications") },
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 20.dp)
                    .padding(bottom = 110.dp)
            ) {

                val newCount = remember(dataToShow) { dataToShow.count { it.isRecent() } }

                Text(
                    text = if (newCount > 0) "$newCount new notifications" else "No new notifications (server data)",
                    color = Color(0xFF7D7796),
                    fontSize = 14.sp,
                    modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 8.dp)
                )

                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    when {
                        isLoading && serverData.isEmpty() -> CircularProgressIndicator()

                        errorMessage != null && serverData.isEmpty() -> {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("Showing offline data", color = Color(0xFF7D7796))
                                Spacer(Modifier.height(8.dp))
                            }
                            NotificationList(
                                dataToShow,
                                onPlay = {
                                    navigator.push(
                                        PlayerScreen(
                                            image = items[0].icon,
                                            title = items[0].text,
                                        )
                                    )
                                },
                            )
                        }

                        else -> NotificationList(
                            dataToShow,
                            onPlay = {
                                navigator.push(
                                    PlayerScreen(
                                        image = items[0].icon,
                                        title = items[0].text,
                                    )
                                )
                            },
                        )
                    }
                }
            }
        }
    }

    @Composable
    private fun NotificationList(list: List<NotificationModel>, onPlay: () -> Unit) {
        LazyColumn(
            contentPadding = PaddingValues(top = 8.dp, bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(list) { item ->
                NotificationCard(
                    notification = item,
                    highlighted = item.isRecent(),
                    onPlay = {
                        onPlay()
                    }
                )
            }
        }
    }

    @Composable
    private fun NotificationCard(
        notification: NotificationModel,
        highlighted: Boolean,
        onPlay: () -> Unit
    ) {
        val lilac = Color(0xFFB99BF7)
        val lilacLight = Color(0xFFF0E9FF)
        val surfaceColor = if (highlighted) lilacLight else Color.White
        val borderColor = if (highlighted) lilac.copy(alpha = 0.9f) else Color(0x1A000000)

        Surface(
            color = surfaceColor,
            shape = RoundedCornerShape(18.dp),
            tonalElevation = if (highlighted) 0.dp else 1.dp,
            shadowElevation = if (highlighted) 0.dp else 2.dp,
            border = BorderStroke(1.dp, borderColor)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.Top
            ) {
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(
                            if (highlighted)
                                Brush.linearGradient(
                                    listOf(Color(0xFFE8D9FF), Color(0xFFD9C8FF))
                                )
                            else
                                Brush.linearGradient(
                                    listOf(Color(0xFFF6F6FA), Color(0xFFEDEDF4))
                                )
                        ),
                    contentAlignment = Alignment.Center
                ) { Text(if (highlighted) "✨" else "🌙", fontSize = 20.sp) }

                Spacer(Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        notification.title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF2F2A41)
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        notification.message,
                        color = Color(0xFF6F6A83),
                        fontSize = 14.sp,
                        lineHeight = 18.sp
                    )

                    Spacer(Modifier.height(12.dp))
                    NotificationPlayButton(text = "Play", tint = Color(0xFFB99BF7), onClick = { onPlay() })

                    Spacer(Modifier.height(10.dp))
                    Text(notification.dateText, color = Color(0xFF8D89A0), fontSize = 12.sp)
                }
            }
        }
    }

    @Composable
    fun NotificationPlayButton(
        text: String,
        tint: Color,
        onClick: () -> Unit
    ) {
        Surface(
            color = tint,
            contentColor = Color.White,
            shape = RoundedCornerShape(22.dp),
            modifier = Modifier.height(40.dp).clickable { onClick() }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .defaultMinSize(minWidth = 120.dp)
                    .padding(horizontal = 16.dp)
                    .height(40.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(22.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.play_filled),
                        contentDescription = "Play icon",
                        modifier = Modifier
                            .size(20.dp)

                    )
                }
                Spacer(Modifier.width(10.dp))
                Text(text, fontWeight = FontWeight.Medium, fontSize = 16.sp)
            }
        }
    }
}


private fun NotificationModel.isRecent(): Boolean {
    val t = dateText.lowercase()
    return "hour" in t || "min" in t || "just" in t || "new" in t
}

/** Local fake data to use while the server is not ready. */
private fun fakeNotifications(count: Int = 80): List<NotificationModel> {
    val titles = listOf(
        "Morning Affirmation Ready",
        "Evening Reflection Time",
        "You're Building Positive Habits",
        "Midday Boost",
        "Gratitude Reminder",
        "Deep Focus Session",
        "Confidence Check-in",
        "Positive Thought Break"
    )
    val messages = listOf(
        "Start your day with positive energy. Your personalized morning affirmation is waiting.",
        "Wind down with a peaceful affirmation to end your day with gratitude and calm.",
        "Consistency is key. Your daily practice is creating lasting positive change in your mindset.",
        "A short affirmation to recharge your afternoon.",
        "Take a moment to notice three things you're grateful for.",
        "Breathe in, breathe out—return to the task with clarity.",
        "Stand tall. You are capable and prepared.",
        "Pause and repeat a kind word to yourself."
    )
    val times = listOf(
        "Just now",
        "5 minutes ago",
        "25 minutes ago",
        "1 hour ago",
        "2 hours ago",
        "6 hours ago",
        "Yesterday",
        "1 day ago"
    )

    return List(count) {
        val i = Random.nextInt(titles.size)
        val j = Random.nextInt(messages.size)
        val k = Random.nextInt(times.size)
        NotificationModel(
            title = titles[i],
            message = messages[j],
            dateText = times[k],
            id = 0
        )
    }
}
