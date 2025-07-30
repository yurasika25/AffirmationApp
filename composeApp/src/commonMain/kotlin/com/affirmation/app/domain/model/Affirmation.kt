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

