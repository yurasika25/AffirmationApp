package com.affirmation.app.presentation.screen.main

import AffirmationToolBar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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
                state.error != null -> Text("Error: ${state.error}")
                state.items.isEmpty() -> Text("No notifications")
                else -> NotificationsList(state.items)
            }
        }
    }
}


@Composable
private fun NotificationsList(items: List<NotificationModel>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(
            start = 20.dp,
            end = 20.dp,
            bottom = 110.dp,
            top = 6.dp
        ),
    ) {
        items(items, key = { it.id }) { data ->
            NotificationRow(data)
        }
    }
}


@Composable
private fun NotificationRow(data: NotificationModel) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha=0.9f)
        ),
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(data.title, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(6.dp))
            Text(data.message, style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(10.dp))
            Text(data.dateText, style = MaterialTheme.typography.labelMedium)
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
