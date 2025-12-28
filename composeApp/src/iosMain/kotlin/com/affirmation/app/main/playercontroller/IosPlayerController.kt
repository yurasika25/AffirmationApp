package com.affirmation.app.main.playercontroller

import com.affirmation.app.presentation.screen.player.audioplayer.AudioSource
import com.affirmation.app.presentation.screen.player.audioplayer.PlayerController
import com.affirmation.app.presentation.screen.player.audioplayer.PlayerState
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.memScoped
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
import platform.AVFAudio.AVAudioSession
import platform.AVFAudio.AVAudioSessionCategoryPlayback
import platform.AVFAudio.AVAudioSessionModeDefault
import platform.AVFAudio.setActive
import platform.AVFoundation.AVPlayer
import platform.AVFoundation.AVPlayerItem
import platform.AVFoundation.currentItem
import platform.AVFoundation.currentTime
import platform.AVFoundation.duration
import platform.AVFoundation.pause
import platform.AVFoundation.play
import platform.AVFoundation.seekToTime
import platform.CoreMedia.CMTimeGetSeconds
import platform.CoreMedia.CMTimeMakeWithSeconds
import platform.Foundation.NSURL

class IosPlayerController : PlayerController {

    private var player: AVPlayer? = null

    private val _state = MutableStateFlow(PlayerState())
    override val state: StateFlow<PlayerState> = _state

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    private var ticker: Job? = null

    init {
        println("IosPlayerController created: $this")
        configureAudioSession()
        ticker = scope.launch {
            while (isActive) {
                publish()
                delay(150)
            }
        }
    }

    override fun setSource(source: AudioSource, playWhenReady: Boolean) {

        _state.value = PlayerState(
            isReady = false,
            isPlaying = false,
            durationMs = 0L,
            positionMs = 0L,
            bufferedPositionMs = 0L,
            error = null

        )
        val url = when (source) {
            is AudioSource.Url -> source.value
            is AudioSource.Asset -> TODO()
            is AudioSource.FilePath -> TODO()
        }

        val nsUrl = NSURL.Companion.URLWithString(url)
        requireNotNull(nsUrl) { "Invalid URL: $url" }

        player?.pause()
        player = null

        val item = AVPlayerItem(uRL = nsUrl)
        player = AVPlayer(playerItem = item)

        _state.value = _state.value.copy(isReady = true)

        if (playWhenReady) play()
    }

    override fun play() {
        player?.play()
        _state.value = _state.value.copy(isPlaying = true)
    }

    override fun pause() {
        player?.pause()
        _state.value = _state.value.copy(isPlaying = false)
    }

    @OptIn(ExperimentalForeignApi::class)
    override fun stop() {
        player?.pause()
        player?.seekToTime(CMTimeMakeWithSeconds(0.0, 1))
        _state.value = _state.value.copy(isPlaying = false, positionMs = 0L)
    }

    @OptIn(ExperimentalForeignApi::class)
    override fun seekTo(positionMs: Long) {
        val seconds = positionMs.toDouble() / 1000.0
        player?.seekToTime(CMTimeMakeWithSeconds(seconds, 1000))
    }

    override fun release() {
        ticker?.cancel()
        ticker = null

        scope.cancel()

        player?.pause()
        player = null

        _state.value = PlayerState()
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun publish() {
        val p = player ?: return

        val posMs =
            (CMTimeGetSeconds(p.currentTime()) * 1000.0)
                .toLong()
                .coerceAtLeast(0L)

        val durSeconds =
            p.currentItem?.duration?.let { CMTimeGetSeconds(it) } ?: 0.0

        val durMs =
            if (durSeconds.isFinite() && durSeconds > 0.0)
                (durSeconds * 1000.0).toLong()
            else 0L

        val currentError = _state.value.error

        _state.value = _state.value.copy(
            isReady = _state.value.isReady || durMs > 0L,
            positionMs = posMs,
            durationMs = durMs,
            bufferedPositionMs = _state.value.bufferedPositionMs,
            error = currentError
        )
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun configureAudioSession() {
        val session = AVAudioSession.Companion.sharedInstance()
        memScoped {
            session.setCategory(
                AVAudioSessionCategoryPlayback,
                AVAudioSessionModeDefault,
                0u,
                null
            )
            session.setActive(true, null)
        }
    }
}