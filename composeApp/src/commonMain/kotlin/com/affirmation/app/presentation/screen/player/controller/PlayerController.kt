package com.affirmation.app.presentation.screen.player.controller

import com.affirmation.app.presentation.screen.player.model.AudioSource
import com.affirmation.app.presentation.screen.player.state.PlayerState
import kotlinx.coroutines.flow.StateFlow

interface PlayerController {
    val state: StateFlow<PlayerState>

    fun setSource(source: AudioSource, playWhenReady: Boolean = false)
    fun play()
    fun pause()
    fun stop()
    fun seekTo(positionMs: Long)
    fun release()
}