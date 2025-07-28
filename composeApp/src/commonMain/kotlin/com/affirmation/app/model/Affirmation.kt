package com.affirmation.app.model


import org.jetbrains.compose.resources.DrawableResource

data class MotivationItem(val icon: DrawableResource, val text: String, val subtitle: String)

data class NavItem(val label: String, val icon: DrawableResource)

