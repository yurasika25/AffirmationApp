package com.affirmation.app.presentation.screen.main

import AffirmationToolBar
import androidx.compose.foundation.background
imimport androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.affirmation.app.domain.model.NotificationModel
import com.affirmation.app.presentation.store.NotificationsIntent
import com.affirmation.app.presentation.store.NotificationsStore
import com.affirmation.app.presentation.theme.AffirmationColors
import com.affirmation.app.presentation.theme.LocalAffirmationColors
import org.koin.compose.koinInject

@Composable
fun NotificationScreen() {

    val colors = LocalAffirmationColors.current
    val store: NotificationsStore = koinInject()
    val state by store.state.collectAsState()

    LaunchedEffect(Unit) {
        store.dispatch(NotificationsIntent.OnAppear)
    }

    DisposableEffect(Unit) {
        onDispose { store.clear() }
    }

    Scaffold(
        topBar = { AffirmationToolBar("Notifications") },
    ) { inner ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            colors.screenGradientTop,
                            colors.screenGradientBottom
                        )
                    )
                )
                .padding(inner)
        ) {
            when {
                state.isLoading -> ShowLoader(colors)

                state.error != null -> CenterMessage(
                    title = "Something went wrong",
                    subtitle = state.error ?: "Unknown error"
                )

                state.items.isEmpty() -> CenterMessage(
                    title = "No notifications",
                    subtitle = "Your updates will appear here."
                )

                else -> NotificationsList(items = state.items, colors)
            }
        }
    }
}

@Composable
private fun NotificationsList(items: List<NotificationModel>, colors: AffirmationColors) {
    LazyColumn(
        contentPadding = PaddingValues(
            start = 20.dp,
            end = 20.dp,
            bottom = 110.dp,
            top = 6.dp
        ), modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {

        items(items, key = { it.id }) { data ->
            NotificationRow(data = data, colors)
        }
    }
}

@Composable
private fun NotificationRow(data: NotificationModel, colors: AffirmationColors) {
    val cardShape = RoundedCornerShape(14.dp)


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(cardShape),
        shape = cardShape,
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.92f)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.Top
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 12.dp)
            ) {
                Text(
                    text = data.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(Modifier.height(6.dp))

                Text(
                    text = data.message,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.75f),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )

                if (data.dateText.isNotBlank()) {
                    Spacer(Modifier.height(10.dp))
                    Text(
                        text = data.dateText,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.60f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            NewBadge()
        }
    }
}

@Composable
private fun NewBadge() {
    val shape = RoundedCornerShape(14.dp)

    Box(
        modifier = Modifier
            .clip(shape)
            .border(width = 1.dp, color = Color(0xFF2F6BFF), shape = RoundedCornerShape(12.dp))
            .padding(horizontal = 10.dp, vertical = 5.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "NEW",
            style = MaterialTheme.typography.labelMedium,
            color = Color(0xFF2F6BFF)
        )
    }
}

@Composable
private fun CenterMessage(
    title: String,
    subtitle: String
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.70f)
            )
        }
    }
}

@Composable
private fun ShowLoader(colors: AffirmationColors) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            strokeWidth = 4.dp,
            color = colors.loaderColor
        )
    }
}
