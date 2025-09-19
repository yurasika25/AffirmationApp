package com.affirmation.app.presentation.ui.screens

import affirmationapp.composeapp.generated.resources.Res
import affirmationapp.composeapp.generated.resources.play_filled
import affirmationapp.composeapp.generated.resources.red_heart
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.affirmation.app.utils.GlobalTopBar
import com.affirmation.app.utils.items
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

class FavoriteScreen : Screen {

    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow

        val pageBg = Color(0xFFFAF7FF)
        val favorites = remember {
            if (items.isNotEmpty()) items.mapIndexed { idx, it ->
                FavoriteUiModel(
                    image = it.icon,
                    tag = listOf("#Self-development", "#Mindfulness", "#Motivation")[idx % 3],
                    title = it.text,
                    description = it.subtitle,
                    timeAgo = demoTimes[idx % demoTimes.size],
                    liked = true
                )
            } else fakeFavorites()
        }

        Scaffold(
            containerColor = pageBg,
            topBar = { GlobalTopBar("My Favorites") },
        ) { inner ->
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(inner)
            ) {
                Spacer(Modifier.height(6.dp))

                Text(
                    "${favorites.size} affirmations saved",
                    color = Color(0xFF7D7796),
                    fontSize = 14.sp,
                    modifier = Modifier.padding(
                        start = 20.dp,
                        end = 20.dp,
                        bottom = 8.dp,
                        top = 0.dp
                    )
                )

                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(favorites) { f ->
                        FavoriteCard(
                            data = f,
                            onPlay = {
                                navigator.push(
                                    PlayerScreen(
                                        image = items[0].icon,
                                        title = items[0].text,
                                    )
                                )
                            },
                            onReadMore = { /* TODO: open details */ },
                            onToggleLike = { /* TODO: toggle like */ }
                        )
                    }
                }
            }
        }
    }
}


@Composable
private fun FavoriteCard(
    data: FavoriteUiModel,
    onPlay: () -> Unit,
    onReadMore: () -> Unit,
    onToggleLike: () -> Unit
) {
    Surface(
        color = Color.White,
        shape = RoundedCornerShape(18.dp),
        border = BorderStroke(1.dp, Color(0x1A000000)),
        tonalElevation = 0.dp,
        shadowElevation = 0.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(132.dp)
                    .clip(RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp))
            ) {
                Image(
                    painter = painterResource(data.image),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                Box(
                    Modifier
                        .matchParentSize()
                        .background(
                            Brush.verticalGradient(
                                0f to Color(0x33000000),
                                1f to Color.Transparent
                            )
                        )
                )

                PillChip(
                    text = data.tag,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(10.dp)
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(10.dp)
                        .size(32.dp)
                        .clickable(onClick = onToggleLike),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.red_heart),
                        contentDescription = "Red filled icon",
                        tint = Color(0xFFFF0000),
                        modifier = Modifier
                            .size(20.dp)
                    )
                }
            }

            Column(Modifier.padding(horizontal = 14.dp, vertical = 12.dp)) {
                Text(
                    data.title,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    color = Color(0xFF2F2A41)
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    data.description,
                    color = Color(0xFF6F6A83),
                    fontSize = 14.sp,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(Modifier.height(12.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    PlayButton(text = "Play", tint = Color(0xFFB99BF7), onClick = onPlay)
                    ReadMorePill(onClick = onReadMore)
                }

                Spacer(Modifier.height(10.dp))
                Text(
                    data.timeAgo,
                    color = Color(0xFF8D89A0),
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
private fun PillChip(text: String, modifier: Modifier = Modifier) {
    Surface(
        color = Color(0xFFF4EEFF),
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
    ) {
        Text(
            text,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
        )
    }
}

@Composable
fun RowScope.PlayButton(
    text: String,
    tint: Color,
    onClick: () -> Unit
) {
    Surface(
        color = tint,
        contentColor = Color.White,
        shape = RoundedCornerShape(22.dp),
        modifier = Modifier
            .height(40.dp)
            .weight(1f)
            .clickable(onClick = onClick)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .size(22.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(Res.drawable.play_filled),
                    contentDescription = "Play icon",
                    modifier = Modifier
                        .size(20.dp)
                )
            }

            Spacer(Modifier.width(10.dp))
            Text(text, fontWeight = FontWeight.Medium, fontSize = 16.sp)
        }
    }
}


@Composable
private fun RowScope.ReadMorePill(onClick: () -> Unit) {
    Surface(
        color = Color.White,
        contentColor = Color(0xFF2F2A41),
        shape = RoundedCornerShape(22.dp),
        border = BorderStroke(1.dp, Color(0x22000000)),
        modifier = Modifier
            .height(40.dp)
            .weight(1f)
            .clickable(onClick = onClick)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text("Read More", fontWeight = FontWeight.Medium, fontSize = 16.sp)
        }
    }
}

private data class FavoriteUiModel(
    val image: DrawableResource,
    val tag: String,
    val title: String,
    val description: String,
    val timeAgo: String,
    val liked: Boolean
)

private val demoDescriptions = listOf(
    "I am capable of achieving anything I set my mind to. My potential is limitless, and I trust in my abilities to grow.",
    "I am at peace with myself and the world around me. I breathe in calm and exhale any tension.",
    "I welcome new opportunities and act with confidence and clarity."
)

private val demoTimes = listOf("2 days ago", "3 days ago", "4 days ago", "1 week ago")

private fun fakeFavorites(count: Int = 6): List<FavoriteUiModel> {
    val placeholder = items.firstOrNull()?.icon
        ?: error("Provide at least one drawable in utils.items for placeholders")

    return List(count) { idx ->
        FavoriteUiModel(
            image = placeholder,
            tag = listOf("#Self-development", "#Mindfulness", "#Motivation")[idx % 3],
            title = listOf(
                "Believe in Yourself",
                "Inner Peace and Calm",
                "Your Daily Motivation"
            )[idx % 3],
            description = demoDescriptions[idx % demoDescriptions.size],
            timeAgo = demoTimes[idx % demoTimes.size],
            liked = true
        )
    }
}
