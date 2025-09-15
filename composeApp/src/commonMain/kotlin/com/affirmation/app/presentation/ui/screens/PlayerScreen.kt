package com.affirmation.app.presentation.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.affirmation.app.utils.items
import com.affirmation.app.utils.monthNames
import kotlinx.coroutines.delay
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

class PlayerScreen(
    private val image: DrawableResource = items.firstOrNull()?.icon
        ?: error("Provide a DrawableResource for PlayerScreen"),
    private val title: String = "Believe in Yourself",
    private val tags: List<String> = listOf("#Self-development", "#Selflove", "#Motivation"),
    private val mantraLineFaded: String = "I am capable of achieving anything",
    private val mantraLineBold: String = "I set my mind to",
    private val durationSec: Int = 30
) : Screen {

    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    override fun Content() {
        val nav = LocalNavigator.currentOrThrow
        val pageBg = Color(0xFFFAF7FF)
        val accent = Color(0xFFB99BF7)

        val today = remember {
            val dt = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            "Today is ${monthNames[dt.monthNumber - 1]} ${dt.dayOfMonth}, ${dt.year}"
        }

        var isPlaying by remember { mutableStateOf(true) }
        var progress by remember { mutableStateOf(0f) }

        LaunchedEffect(isPlaying) {
            while (isPlaying) {
                delay(1000)
                progress = (progress + 1f / durationSec).coerceAtMost(1f)
                if (progress >= 1f) isPlaying = false
            }
        }

        Scaffold(
            containerColor = pageBg,
            topBar = {
                PlayerTopBar(
                    title = "Affirmation Player",
                    date = today,
                    onBack = { nav.pop() },
                    onShare = { /* TODO: share */ }
                )
            }
        ) { inner ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(inner)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp)
            ) {

                Surface(
                    color = Color.White,
                    shape = RoundedCornerShape(20.dp),
                    tonalElevation = 0.dp,
                    shadowElevation = 0.dp,
                    border = BorderStroke(1.dp, Color(0x1A000000)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(Modifier.padding(14.dp)) {

                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            tags.forEach { Chip(it) }
                        }

                        Spacer(Modifier.height(12.dp))

                        Image(
                            painter = painterResource(image),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(170.dp)
                                .clip(RoundedCornerShape(18.dp))
                        )

                        Spacer(Modifier.height(18.dp))

                        Text(
                            mantraLineFaded,
                            color = Color(0xFFBFBAD2),
                            fontSize = 22.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(Modifier.height(6.dp))
                        Text(
                            mantraLineBold,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 24.sp,
                            color = Color(0xFF2F2A41),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            title,
                            color = Color(0xFF6F6A83),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(Modifier.height(18.dp))

                        Slider(
                            value = progress,
                            onValueChange = { progress = it },
                            colors = SliderDefaults.colors(
                                thumbColor = accent,
                                activeTrackColor = accent.copy(alpha = 0.6f),
                                inactiveTrackColor = accent.copy(alpha = 0.25f)
                            )
                        )
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(formatTime(progress, durationSec), color = accent, fontSize = 12.sp)
                            Text(formatTime(1f, durationSec), color = accent, fontSize = 12.sp)
                        }

                        Spacer(Modifier.height(10.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            ControlPill("⏮") { progress = 0f }
                            PlayButton(isPlaying = isPlaying, tint = accent) {
                                isPlaying = !isPlaying
                            }
                            ControlPill("⏭") { progress = 1f }
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))

                Surface(
                    color = Color.White,
                    shape = RoundedCornerShape(18.dp),
                    border = BorderStroke(1.dp, Color(0x1A000000)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 18.dp)
                            .fillMaxWidth()
                            .clickable { /* TODO: toggle favorite */ }
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(Color(0x14B99BF7)),
                            contentAlignment = Alignment.Center
                        ) { Text("♡", color = Color(0xFFB99BF7), fontSize = 18.sp) }

                        Spacer(Modifier.width(12.dp))
                        Text("Add to favorites", fontSize = 16.sp)
                    }
                }

                Spacer(Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun PlayerTopBar(
    title: String,
    date: String,
    onBack: () -> Unit,
    onShare: () -> Unit
) {
    Column(Modifier.fillMaxWidth().padding(top = 8.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
        ) {
            Text("←",
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable(onClick = onBack)
                    .padding(10.dp),
                color = Color(0xFF6A5AE0),
                fontSize = 18.sp
            )
            Text("🔗",
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable(onClick = onShare)
                    .padding(10.dp),
                color = Color(0xFF6A5AE0),
                fontSize = 18.sp
            )
        }
        Spacer(Modifier.height(8.dp))
        Column(Modifier.padding(horizontal = 16.dp)) {
            Text(title, fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
            Spacer(Modifier.height(2.dp))
            Text(date, color = Color(0xFF7D7796), fontSize = 14.sp)
        }
        Spacer(Modifier.height(10.dp))
    }
}

@Composable
private fun Chip(text: String) {
    Surface(
        color = Color(0xFFF0E9FF),
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(text, modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp))
    }
}

@Composable
private fun ControlPill(glyph: String, onClick: () -> Unit) {
    Text(
        glyph,
        color = Color(0xFF8B79DA),
        fontSize = 28.sp,
        modifier = Modifier
            .padding(end = 30.dp)
            .padding(start = 30.dp)
            .clip(CircleShape)
            .padding(vertical = 8.dp)
            .clickable(onClick = onClick)
    )
}

@Composable
private fun PlayButton(isPlaying: Boolean, tint: Color, onClick: () -> Unit) {
    Surface(
        color = tint,
        contentColor = Color.White,
        shape = CircleShape,
        modifier = Modifier
            .size(76.dp)
            .clickable(onClick = onClick)
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            Text(if (isPlaying) "⏸" else "▶", fontSize = 28.sp)
        }
    }
}

private fun formatTime(progress: Float, durationSec: Int): String {
    val total = (durationSec * progress).toInt().coerceIn(0, durationSec)
    val m = total / 60
    val s = total % 60
    return "${m}:${s.toString().padStart(2, '0')}"
}
