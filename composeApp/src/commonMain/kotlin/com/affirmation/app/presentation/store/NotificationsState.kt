package com.affirmation.app.presentation.store

import com.affirmation.app.domain.model.NotificationModel

data class NotificationsState(
    val isLoading: Boolean = false,
    val items: List<NotificationModel> = emptyList(),
    val error: String? = null
)

sealed interface NotificationsIntent {
    data object OnAppear : NotificationsIntent
    data object OnRefresh : NotificationsIntent
}