package com.affirmation.app.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class NotificationData(
    val id: Int,
    val title: String,
    val message: String,
    val dateText: String
)
