@file:OptIn(ExperimentalMaterial3Api::class)

package com.affirmation.app.presentation.screen.player

import affirmationapp.composeapp.generated.resources.Res
import affirmationapp.composeapp.generated.resources.im_list
import affirmationapp.composeapp.generated.resources.im_back_btn
import affirmationapp.composeapp.generated.resources.im_download
import affirmationapp.composeapp.generated.resources.im_favorite
import affirmationapp.composeapp.generated.resources.im_repeat
import affirmationapp.composeapp.generated.resources.im_share
import affirmationapp.composeapp.generated.resources.im_shuffle
import affirmationapp.composeapp.generated.resources.move_back
import affirmationapp.composeapp.generated.resources.move_forvard
import affirmationapp.composeapp.generated.resources.im_pause
import affirmationapp.composeapp.generated.resources.im_play
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
import com.affirmation.app.utils.HideBottomBar
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import kotlin.math.roundToInt

class MusicPlayerScreen(
    private val imageUrl: String,
    private val title: String,
) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        HideBottomBar()

        MorningEnergyContent(
            navigator = navigator,
            imageUrl = imageUrl,
            title = title,
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
        )
    }
}

@Composable
fun MorningEnergyContent(
    navigator: Navigator,
    imageUrl: String,
    title: String = "Morning Energy",
    trackTitle: String = "Sunrise Energy",
    durationSec: Int = 120,
    initialPositionSec: Int = 42,
    isPlayingInitial: Boolean = false,
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
) {
    var isPlaying by remember { mutableStateOf(isPlayingInitial) }
    var positionSec by remember { mutableStateOf(initialPositionSec.coerceIn(0, durationSec)) }

    val progress = remember(positionSec, durationSec) {
        if (durationSec <= 0) 0f else positionSec.toFloat() / durationSec.toFloat()
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

        // Bottom sheet player
        PlayerBottomSheet(
            trackTitle = trackTitle,
            positionSec = positionSec,
            durationSec = durationSec,
            progress = progress,
            isPlaying = isPlaying,
            icShuffle = icShuffle,
            icPrev = icPrev,
            icNext = icNext,
            icRepeat = icRepeat,
            icPlay = icPlay,
            icPause = icPause,
            onSeekProgress = { newProgress ->
                val newPos = (newProgress * durationSec).roundToInt().coerceIn(0, durationSec)
                positionSec = newPos
                onSeek(newPos)
            },
            onShuffleClick = onShuffleClick,
            onPrevClick = onPrevClick,
            onPlayPauseClick = {
                isPlaying = !isPlaying
                onPlayPauseClick(isPlaying)
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
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        ActionChip(icon = icFavorite, label = "Favorite", onClick = onFavoriteClick)
        ActionChip(icon = icShare, label = "Share", onClick = onShareClick)
        ActionChip(icon = icSave, label = "Save", onClick = onSaveClick)
        ActionChip(icon = icLists, label = "Lists", onClick = onListsClick)
    }
}

// right group of buttons
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
                .background(Color.White.copy(alpha = 0.64f))
                .clickable(onClick = onClick),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(icon),
                contentDescription = label,
                modifier = Modifier.size(22.dp),
                colorFilter = tint(Color(0xFF03237B))
            )
        }

        Spacer(Modifier.height(6.dp))

        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium.copy(
                color = Color.White.copy(alpha = 0.64f),
                fontWeight = FontWeight.Medium
            )
        )
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
    onShuffleClick: () -> Unit,
    onPrevClick: () -> Unit,
    onPlayPauseClick: () -> Unit,
    onNextClick: () -> Unit,
    onRepeatClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
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
                // shuffle button
                SmallPlayerButton(onClick = onShuffleClick) {
                    IconImage(
                        icon = icShuffle,
                        contentDescription = "Shuffle",
                        size = 22.dp,
                        tint = Color.White.copy(alpha = 0.80f)
                    )
                }

                Spacer(Modifier.weight(1f))

                // previous button
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

                // next button
                SmallPlayerButton(onClick = onNextClick) {
                    IconImage(
                        icon = icNext,
                        contentDescription = "Next",
                        size = 36.dp,
                        tint = Color.White.copy(alpha = 0.80f)
                    )
                }

                Spacer(Modifier.weight(1f))

                // repeat button
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
        modifier = Modifier
            .clickable(onClick = onClick),
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
    val painter = painterResource(icon)

    Image(
        painter = painter,
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
    val painter = painterResource(icon)

    Image(
        painter = painter,
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
            .background(Color.White.copy(alpha = 0.64f))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(icon),
            contentDescription = "Back",
            modifier = Modifier.size(20.dp),
            colorFilter = tint(Color(0xFF03237B))
        )
    }
}
