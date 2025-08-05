package com.affirmation.app.domain.model


import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.DrawableResource

data class MotivationItem(val icon: DrawableResource, val text: String, val subtitle: String)

data class NavItem(val label: String, val icon: DrawableResource)


@Serializable
data class AffirmationData(
    val id: Int,
    val text: String
)

@Serializable
data class UserProfile(
    val firstName: String,
    val latestName: String,
    val age: Int,
    val gender: String,
    val phoneNumber: String,
    val email: String
)

