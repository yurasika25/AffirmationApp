package com.affirmation.app.presentation.screen.player.state

data class PlayerState(
    val isReady: Boolean = false,
    val isPlaying: Boolean = false,
    val durationMs: Long = 0L,
    val positionMs: Long = 0L,
    val bufferedPositionMs: Long = 0L,
    val error: String? = null
)