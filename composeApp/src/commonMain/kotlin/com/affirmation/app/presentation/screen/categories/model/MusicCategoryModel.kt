package com.affirmation.app.presentation.screen.categories.model

import androidx.compose.runtime.Immutable

@Immutable
data class MusicCategoryModel(
    val id: String,
    val title: String,
    val subtitle: String,
    val imageUrl: String,
    val audioUrl: String
)