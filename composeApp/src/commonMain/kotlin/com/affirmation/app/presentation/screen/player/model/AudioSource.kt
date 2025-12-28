package com.affirmation.app.presentation.screen.player.model

sealed interface AudioSource {
    data class Url(val value: String) : AudioSource
    data class FilePath(val value: String) : AudioSource
    data class Asset(val name: String) : AudioSource
}