package com.affirmation.app.presentation.screen.player.audioplayer

import kotlinx.coroutines.flow.StateFlow

data class PlayerState(
    val isReady: Boolean = false,
    val isPlaying: Boolean = false,
    val durationMs: Long = 0L,
    val positionMs: Long = 0L,
    val bufferedPositionMs: Long = 0L,
    val error: String? = null
)

sealed interface AudioSource {
    data class Url(val value: String) : AudioSource
    data class FilePath(val value: String) : AudioSource
    data class Asset(val name: String) : AudioSource
}

interface PlayerController {
    val state: StateFlow<PlayerState>

    fun setSource(source: AudioSource, playWhenReady: Boolean = false)
    fun play()
    fun pause()
    fun stop()
    fun seekTo(positionMs: Long)
    fun release()
}
