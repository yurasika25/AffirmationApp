package com.affirmation.app.presentation.ui.screens

import affirmationapp.composeapp.generated.resources.Res
import affirmationapp.composeapp.generated.resources.arrow_back
import affirmationapp.composeapp.generated.resources.heart_outline
import affirmationapp.composeapp.generated.resources.play_filled
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import com.affirmation.app.utils.items
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

class AffirmationDetailsScreen(
    private val image: DrawableResource = items.firstOrNull()?.icon
        ?: error("Provide a DrawableResource for AffirmationDetailsScreen"),
    private val bannerTitle: String = "BELIEVE IN YOURSELF",
    private val tags: List<String> = listOf("#Self-development", "#Selflove", "#Motivation"),
    private val mainText: String = "I am capable of achieving anything I set my mind to. My potential is limitless, and I trust in my abilities to overcome any challenge.",
    private val description: String = "A powerful affirmation to boost your self-confidence and remind you of your inner strength. Perfect for starting your day with a positive mindset."
) : Screen {

    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    override fun Content() {
        val pageBg = Color(0xFFFAF7FF)
        val accent = Color(0xFFB99BF7)

        Scaffold(containerColor = pageBg) { inner ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(inner)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp)
            ) {
                Icon(
                    painter = painterResource(Res.drawable.arrow_back),
                    contentDescription = "Arrow back icon",
                    tint = Color(0xFF9985D0),
                    modifier = Modifier
                        .size(32.dp)
                )
                Spacer(Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(26.dp))
                ) {
                    Image(
                        painter = painterResource(image),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(190.dp)
                    )

                    Box(
                        Modifier
                            .matchParentSize()
                            .background(
                                Brush.verticalGradient(
                                    0f to Color(0x33000000),
                                    0.5f to Color.Transparent,
                                    1f to Color(0x88000000)
                                )
                            )
                    )

                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(14.dp)
                    ) {
                        Text(
                            bannerTitle,
                            color = Color.White,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 26.sp
                        )
                        Spacer(Modifier.height(12.dp))
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            tags.forEach { Chip(it) }
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))

                Surface(
                    color = Color.White,
                    shape = RoundedCornerShape(20.dp),
                    border = BorderStroke(1.dp, Color(0x1A000000)),
                    tonalElevation = 0.dp,
                    shadowElevation = 0.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            mainText,
                            textAlign = TextAlign.Center,
                            fontSize = 20.sp,
                            lineHeight = 26.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF1F1A33)
                        )
                        Spacer(Modifier.height(14.dp))
                        Text(
                            description,
                            textAlign = TextAlign.Center,
                            color = Color(0xFFB0A9C5),
                            lineHeight = 20.sp
                        )

                        Spacer(Modifier.height(18.dp))

                        Surface(
                            color = accent,
                            contentColor = Color.White,
                            shape = RoundedCornerShape(18.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp)
                                .clickable { /* TODO: play */ }
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(24.dp),
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
                                Text("Play", fontWeight = FontWeight.Medium, fontSize = 16.sp)
                            }
                        }

                        Spacer(Modifier.height(12.dp))

                        Surface(
                            color = Color.White,
                            shape = RoundedCornerShape(18.dp),
                            border = BorderStroke(1.dp, Color(0x22000000)),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp)
                                .clickable { /* TODO: toggle favorite */ }
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 14.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(28.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        painter = painterResource(Res.drawable.heart_outline),
                                        contentDescription = "Purple heart icon",
                                        tint = Color(0xFF9985D0),
                                        modifier = Modifier
                                            .size(32.dp)
                                    )
                                }

                                Spacer(Modifier.width(10.dp))
                                Text("Add to favorites", fontSize = 16.sp)
                            }
                        }
                    }
                }

                Spacer(Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun Chip(text: String) {
    Surface(
        color = Color(0xFFF0E9FF),
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(
            text,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            style = MaterialTheme.typography.labelLarge
        )
    }
}
