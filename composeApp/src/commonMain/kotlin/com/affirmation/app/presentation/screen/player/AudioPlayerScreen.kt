@file:OptIn(ExperimentalMaterial3Api::class)

package com.affirmation.app.presentation.screen.player

import affirmationapp.composeapp.generated.resources.Res
import affirmationapp.composeapp.generated.resources.im_back_btn
import affirmationapp.composeapp.generated.resources.im_download
import affirmationapp.composeapp.generated.resources.im_favorite
import affirmationapp.composeapp.generated.resources.im_list
import affirmationapp.composeapp.generated.resources.im_pause
import affirmationapp.composeapp.generated.resources.im_play
import affirmationapp.composeapp.generated.resources.im_repeat
import affirmationapp.composeapp.generated.resources.im_share
import affirmationapp.composeapp.generated.resources.im_shuffle
import affirmationapp.composeapp.generated.resources.move_back
import affirmationapp.composeapp.generated.resources.move_forvard
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter.Companion.tint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil3.compose.AsyncImage
import com.affirmation.app.presentation.screen.player.controller.PlayerController
import com.affirmation.app.presentation.screen.player.model.AudioSource
import com.affirmation.app.presentation.screen.player.state.PlayerState
import com.affirmation.app.utils.HideBottomBar
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import kotlin.math.roundToLong

class AudioPlayerScreen(
    private val imageUrl: String,
    private val title: String,
    private val audioUrl: String = ""
) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        HideBottomBar()

        val controller = koinInject<PlayerController>()
        val playerState by controller.state.collectAsState()


        LaunchedEffect(controller, audioUrl) {
            controller.setSource(
                source = AudioSource.Url(audioUrl),
                playWhenReady = true
            )
        }

        DisposableEffect(Unit) {
            onDispose { controller.release() }
        }

        MorningEnergyContent(
            navigator = navigator,
            imageUrl = imageUrl,
            title = title,
            url = audioUrl,
            icBack = Res.drawable.im_back_btn,
            icFavorite = Res.drawable.im_favorite,
            icShare = Res.drawable.im_share,
            icSave = Res.drawable.im_download,
            icLists = Res.drawable.im_list,
            icShuffle = Res.drawable.im_shuffle,
            icPrev = Res.drawable.move_back,
            icNext = Res.drawable.move_forvard,
            icRepeat = Res.drawable.im_repeat,
            icPlay = Res.drawable.im_play,
            icPause = Res.drawable.im_pause,
            playerState = playerState,
            onPlay = { controller.play() },
            onPause = { controller.pause() },
            onStop = { controller.stop() },
            onSeekMs = { ms -> controller.seekTo(ms) },
        )
    }
}

@Composable
fun MorningEnergyContent(
    navigator: Navigator,
    imageUrl: String,
    title: String = "Morning Energy",
    url: String,
    trackTitle: String = "Sunrise Energy",
    icBack: DrawableResource,
    icFavorite: DrawableResource,
    icShare: DrawableResource,
    icSave: DrawableResource,
    icLists: DrawableResource,
    icShuffle: DrawableResource,
    icPrev: DrawableResource,
    icNext: DrawableResource,
    icRepeat: DrawableResource,
    icPlay: DrawableResource,
    icPause: DrawableResource,

    onBackClick: () -> Unit = { navigator.pop() },
    onFavoriteClick: () -> Unit = {},
    onShareClick: () -> Unit = {},
    onSaveClick: () -> Unit = {},
    onListsClick: () -> Unit = {},
    onShuffleClick: () -> Unit = {},
    onPrevClick: () -> Unit = {},
    onPlayPauseClick: (Boolean) -> Unit = {},
    onNextClick: () -> Unit = {},
    onRepeatClick: () -> Unit = {},
    onSeek: (Int) -> Unit = {},

    playerState: PlayerState,
    onPlay: () -> Unit,
    onPause: () -> Unit,
    onStop: () -> Unit,
    onSeekMs: (Long) -> Unit,
) {
    val durationMs = playerState.durationMs.coerceAtLeast(0L)
    val positionMs = playerState.positionMs.coerceIn(
        0L,
        if (durationMs > 0L) durationMs else Long.MAX_VALUE
    )

    val durationSecReal = (durationMs / 1000L).toInt().coerceAtLeast(0)
    val positionSecReal = (positionMs / 1000L).toInt().coerceIn(
        0,
        if (durationSecReal > 0) durationSecReal else Int.MAX_VALUE
    )


    var userSeeking by remember(url) { mutableStateOf(false) }
    var seekProgress by remember(url) { mutableStateOf(0f) }


    LaunchedEffect(durationMs, positionMs, userSeeking) {
        if (!userSeeking) {
            seekProgress = if (durationMs > 0L) {
                (positionMs.toDouble() / durationMs.toDouble())
                    .toFloat()
                    .coerceIn(0f, 1f)
            } else {
                0f
            }
        }
    }


    val progress = remember(userSeeking, seekProgress, durationMs, positionMs) {
        when {
            durationMs <= 0L -> 0f
            userSeeking -> seekProgress.coerceIn(0f, 1f)
            else -> (positionMs.toDouble() / durationMs.toDouble()).toFloat().coerceIn(0f, 1f)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {

        // Background image
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF1B1B1F))
        )

        // Overlay gradient for readability
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color.Black.copy(alpha = 0.10f),
                            Color.Black.copy(alpha = 0.10f),
                            Color.Black.copy(alpha = 0.28f),
                            Color.Black.copy(alpha = 0.55f)
                        )
                    )
                )
        )

        // Top bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
        ) {
            IconButtonChip(
                icon = icBack,
                onClick = onBackClick,
                modifier = Modifier.align(Alignment.CenterStart)
            )

            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White.copy(alpha = 0.80f)
                ),
                modifier = Modifier.align(Alignment.Center)
            )
        }

        // Right actions
        RightActionsColumn(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 18.dp),
            icFavorite = icFavorite,
            icShare = icShare,
            icSave = icSave,
            icLists = icLists,
            onFavoriteClick = onFavoriteClick,
            onShareClick = onShareClick,
            onSaveClick = onSaveClick,
            onListsClick = onListsClick
        )

        playerState.error?.let { err ->
            Text(
                text = err,
                color = Color.Red.copy(alpha = 0.85f),
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 210.dp)
            )
        }

        // Bottom sheet player
        PlayerBottomSheet(
            trackTitle = trackTitle,
            positionSec = positionSecReal,
            durationSec = durationSecReal,
            progress = progress,
            isPlaying = playerState.isPlaying,

            icShuffle = icShuffle,
            icPrev = icPrev,
            icNext = icNext,
            icRepeat = icRepeat,
            icPlay = icPlay,
            icPause = icPause,

            onSeekProgress = { newProgress ->
                userSeeking = true
                seekProgress = newProgress.coerceIn(0f, 1f)

                if (durationMs > 0L) {
                    val targetMs = (durationMs.toDouble() * seekProgress.toDouble()).roundToLong()
                        .coerceIn(0L, durationMs)
                    onSeekMs(targetMs)

                    val targetSec = (targetMs / 1000L).toInt()
                    onSeek(targetSec)
                }
            },
            onSeekFinished = {
                userSeeking = false
            },

            onShuffleClick = onShuffleClick,
            onPrevClick = onPrevClick,
            onPlayPauseClick = {
                val nowPlaying = playerState.isPlaying
                if (nowPlaying) onPause() else onPlay()
                onPlayPauseClick(!nowPlaying)
            },
            onNextClick = onNextClick,
            onRepeatClick = onRepeatClick,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .padding(horizontal = 14.dp, vertical = 12.dp)
        )
    }
}

@Composable
private fun RightActionsColumn(
    modifier: Modifier = Modifier,
    icFavorite: DrawableResource,
    icShare: DrawableResource,
    icSave: DrawableResource,
    icLists: DrawableResource,
    onFavoriteClick: () -> Unit,
    onShareClick: () -> Unit,
    onSaveClick: () -> Unit,
    onListsClick: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ActionChip(icon = icFavorite, label = "Favorite", onClick = onFavoriteClick)
        ActionChip(icon = icShare, label = "Share", onClick = onShareClick)
        ActionChip(icon = icSave, label = "Save", onClick = onSaveClick)
        ActionChip(icon = icLists, label = "Lists", onClick = onListsClick)
    }
}

@Composable
private fun ActionChip(
    icon: DrawableResource,
    label: String,
    onClick: () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(12.dp))
                .clickable(onClick = onClick),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(icon),
                contentDescription = label,
                modifier = Modifier.size(24.dp),
                colorFilter = tint(Color(0xFFECECEC))
            )
        }

//        Spacer(Modifier.height(6.dp))

//        Text(
//            text = label,
//            style = MaterialTheme.typography.labelMedium.copy(
//                color = Color.White.copy(alpha = 0.64f),
//                fontWeight = FontWeight.Medium
//            )
//        )
    }
}

@Composable
private fun PlayerBottomSheet(
    trackTitle: String,
    positionSec: Int,
    durationSec: Int,
    progress: Float,
    isPlaying: Boolean,

    icShuffle: DrawableResource,
    icPrev: DrawableResource,
    icNext: DrawableResource,
    icRepeat: DrawableResource,
    icPlay: DrawableResource,
    icPause: DrawableResource,

    onSeekProgress: (Float) -> Unit,
    onSeekFinished: () -> Unit,

    onShuffleClick: () -> Unit,
    onPrevClick: () -> Unit,
    onPlayPauseClick: () -> Unit,
    onNextClick: () -> Unit,
    onRepeatClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        color = Color.White.copy(alpha = 0.10f),
        tonalElevation = 0.dp,
        shadowElevation = 0.dp
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = trackTitle,
                style = MaterialTheme.typography.titleMedium.copy(
                    color = Color.White.copy(alpha = 0.80f),
                    fontWeight = FontWeight.SemiBold
                )
            )

            Spacer(Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = formatTime(positionSec),
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = Color.White.copy(alpha = 0.80f)
                    )
                )

                Spacer(Modifier.width(10.dp))

                Slider(
                    value = progress.coerceIn(0f, 1f),
                    onValueChange = onSeekProgress,
                    onValueChangeFinished = onSeekFinished,
                    modifier = Modifier.weight(1f),
                    colors = SliderDefaults.colors(
                        thumbColor = Color.White.copy(alpha = 0.80f),
                        activeTrackColor = Color.White.copy(alpha = 0.80f),
                        inactiveTrackColor = Color.White.copy(alpha = 0.28f)
                    )
                )

                Spacer(Modifier.width(10.dp))

                Text(
                    text = formatTime(durationSec),
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = Color.White.copy(alpha = 0.80f)
                    )
                )
            }

            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SmallPlayerButton(onClick = onShuffleClick) {
                    IconImage(
                        icon = icShuffle,
                        contentDescription = "Shuffle",
                        size = 22.dp,
                        tint = Color.White.copy(alpha = 0.80f)
                    )
                }

                Spacer(Modifier.weight(1f))

                SmallPlayerButton(onClick = onPrevClick) {
                    IconImage(
                        icon = icPrev,
                        contentDescription = "Previous",
                        size = 36.dp,
                        tint = Color.White.copy(alpha = 0.80f)
                    )
                }

                Spacer(Modifier.width(30.dp))

                BigPlayPauseButton(
                    isPlaying = isPlaying,
                    iconPlay = icPlay,
                    iconPause = icPause,
                    onClick = onPlayPauseClick
                )

                Spacer(Modifier.width(30.dp))

                SmallPlayerButton(onClick = onNextClick) {
                    IconImage(
                        icon = icNext,
                        contentDescription = "Next",
                        size = 36.dp,
                        tint = Color.White.copy(alpha = 0.80f)
                    )
                }

                Spacer(Modifier.weight(1f))

                SmallPlayerButton(onClick = onRepeatClick) {
                    IconImage(
                        icon = icRepeat,
                        contentDescription = "Repeat",
                        size = 22.dp,
                        tint = Color.White.copy(alpha = 0.80f)
                    )
                }
            }
        }
    }
}

@Composable
private fun BigPlayPauseButton(
    isPlaying: Boolean,
    iconPlay: DrawableResource,
    iconPause: DrawableResource,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier.clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        IconImagePlayPause(
            icon = if (isPlaying) iconPause else iconPlay,
            contentDescription = if (isPlaying) "Pause" else "Play",
        )
    }
}

@Composable
private fun SmallPlayerButton(
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

@Composable
private fun IconImage(
    icon: DrawableResource,
    contentDescription: String,
    size: Dp = 18.dp,
    tint: Color = Color(0xFF03237B)
) {
    Image(
        painter = painterResource(icon),
        contentDescription = contentDescription,
        modifier = Modifier.size(size),
        colorFilter = tint(tint)
    )
}

@Composable
private fun IconImagePlayPause(
    icon: DrawableResource,
    contentDescription: String
) {
    Image(
        painter = painterResource(icon),
        contentDescription = contentDescription,
        modifier = Modifier.size(60.dp),
        colorFilter = tint(Color.White.copy(alpha = 0.80f))
    )
}

private fun formatTime(totalSeconds: Int): String {
    val s = totalSeconds.coerceAtLeast(0)
    val m = s / 60
    val r = s % 60
    return "${m}:${r.toString().padStart(2, '0')}"
}

@Composable
private fun IconButtonChip(
    icon: DrawableResource,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(44.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(icon),
            contentDescription = "Back",
            modifier = Modifier.size(20.dp),
            colorFilter = tint(Color(0xFFDEDEDE))
        )
    }
}
