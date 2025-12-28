package com.affirmation.app.main.playercontroller

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.affirmation.app.presentation.screen.player.audioplayer.AudioSource
import com.affirmation.app.presentation.screen.player.audioplayer.PlayerController
import com.affirmation.app.presentation.screen.player.audioplayer.PlayerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.math.max

class AndroidPlayerController(
    context: Context
) : PlayerController {

    private val player: ExoPlayer = ExoPlayer.Builder(context).build()

    private val _state = MutableStateFlow(PlayerState())
    override val state: StateFlow<PlayerState> = _state

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    private var tickerJob: Job? = null

    init {
        player.addListener(object : Player.Listener {

            override fun onPlaybackStateChanged(playbackState: Int) {
                updateTicker()
                publishState()
            }

            override fun onIsPlayingChanged(isPlaying: Boolean) {
                updateTicker()
                publishState()
            }

            override fun onPlayerError(error: PlaybackException) {
                _state.value = _state.value.copy(
                    error = error.message ?: "Playback error"
                )
                updateTicker()
                publishState()
            }
        })

        updateTicker()
        publishState()
    }

    override fun setSource(source: AudioSource, playWhenReady: Boolean) {
        _state.value = _state.value.copy(error = null, isReady = false)

        val mediaItem = when (source) {
            is AudioSource.Url -> MediaItem.fromUri(source.value)
            is AudioSource.FilePath -> MediaItem.fromUri(source.value)
            is AudioSource.Asset -> {
                throw IllegalArgumentException("Asset source not implemented on Android in this sample")
            }
        }

        player.setMediaItem(mediaItem)
        player.prepare()
        player.playWhenReady = playWhenReady

        updateTicker()
        publishState()
    }

    override fun play() {
        player.play()
        updateTicker()
        publishState()
    }

    override fun pause() {
        player.pause()
        updateTicker()
        publishState()
    }

    override fun stop() {
        player.stop()
        updateTicker()
        publishState()
    }

    override fun seekTo(positionMs: Long) {
        player.seekTo(max(0L, positionMs))
        publishState()
    }

    override fun release() {
        stopTicker()
        scope.cancel()
        player.release()
    }

    private fun updateTicker() {
        val shouldTick =
            player.isPlaying ||
                    player.playbackState == Player.STATE_BUFFERING ||
                    player.playbackState == Player.STATE_READY

        if (shouldTick) startTicker() else stopTicker()
    }

    private fun startTicker() {
        if (tickerJob?.isActive == true) return
        tickerJob = scope.launch {
            while (isActive) {
                publishState()
                delay(150)
            }
        }
    }

    private fun stopTicker() {
        tickerJob?.cancel()
        tickerJob = null
    }

    private fun publishState() {
        val duration = player.duration.takeIf { it > 0 } ?: 0L
        val position = player.currentPosition.coerceAtLeast(0L)
        val buffered = player.bufferedPosition.coerceAtLeast(0L)

        val ready = player.playbackState == Player.STATE_READY || duration > 0L

        val currentError = _state.value.error

        val clearedError = if (ready) null else currentError

        _state.value = _state.value.copy(
            isReady = ready,
            isPlaying = player.isPlaying,
            durationMs = duration,
            positionMs = position,
            bufferedPositionMs = buffered,
            error = clearedError
        )
    }
}