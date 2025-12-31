@file:OptIn(ExperimentalMaterial3Api::class)

package com.affirmation.app.presentation.screen.main

import affirmationapp.composeapp.generated.resources.Res
import affirmationapp.composeapp.generated.resources.im_book
import affirmationapp.composeapp.generated.resources.im_cards
import affirmationapp.composeapp.generated.resources.im_music
import affirmationapp.composeapp.generated.resources.im_photo
import affirmationapp.composeapp.generated.resources.im_video
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import coil3.compose.AsyncImage
import com.affirmation.app.presentation.nav.ext.root
import com.affirmation.app.presentation.screen.categories.BookScreen
import com.affirmation.app.presentation.screen.categories.CardsScreen
import com.affirmation.app.presentation.screen.categories.PhotoScreen
import com.affirmation.app.presentation.screen.categories.VideoScreen
import com.affirmation.app.presentation.screen.categories.MusicLibraryScreen
import com.affirmation.app.presentation.theme.dancingSemiBoldFont
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun InspirationHomeContent(
    modifier: Modifier = Modifier,
    greetingName: String = "Yurii",
    heroImageUrl: String = "https://images.unsplash.com/photo-1501785888041-af3ef285b470?w=1600&auto=format&fit=crop",
) {
    val navigator = LocalNavigator.current

    val bg = Color(0xFFEFF3FF)
    val surfaceCard = Color(0xFFFFFFFF).copy(alpha = 0.78f)
    val chipBg = Color(0xFFFFFFFF)
    val chipBorder = Color(0xFFE4E8F5)
    val onBg = Color(0xFF111827)

    val categories = sampleCategories()
    val popular = samplePopular()
    val trending = sampleTrending()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(bg)
    ) {
        AsyncImage(
            model = heroImageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            bg.copy(alpha = 0.65f),
                            bg
                        ),
                        startY = 120f
                    )
                )
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 140.dp)
        ) {
            item { Spacer(Modifier.height(8.dp)) }

            item {
                TopBar(
                    greetingName = greetingName,
                    onBg = onBg
                )
            }

            item { Spacer(Modifier.height(18.dp)) }

            item {
                QuoteCard(
                    surfaceCard = surfaceCard,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item { Spacer(Modifier.height(18.dp)) }

            item {
                SectionHeader(
                    title = "Categories",
                    subtitle = "Choose what inspires you",
                    onBg = onBg
                )
            }

            item { Spacer(Modifier.height(12.dp)) }

            item {
                CategoryRow(
                    items = categories,
                    chipBg = chipBg,
                    chipBorder = chipBorder,
                    onCategoryClick = { category ->
                        val screen: Screen = when (category.label) {
                            "Cards" -> CardsScreen()
                            "Music" -> MusicLibraryScreen()
                            "Video" -> VideoScreen()
                            "Photo" -> PhotoScreen()
                            "Book" -> BookScreen()
                            else -> { throw IllegalArgumentException("Unknown category: ${category.label}") }
                        }
                        navigator?.root()?.push(screen)
                    }
                )
            }

            item { Spacer(Modifier.height(18.dp)) }

            item {
                SectionHeader(
                    title = "Popular",
                    subtitle = "Most played this week",
                    onBg = onBg
                )
            }

            item { Spacer(Modifier.height(12.dp)) }

            item { CardRow(items = popular, with = 159.dp, height = 192.dp) }

            item { Spacer(Modifier.height(18.dp)) }

            item {
                SectionHeader(
                    title = "Trending",
                    subtitle = "What's trending today",
                    onBg = onBg
                )
            }

            item { Spacer(Modifier.height(12.dp)) }

            item { CardRow(items = trending, with = 194.dp, height = 237.dp) }
        }
    }
}

@Composable
private fun TopBar(
    greetingName: String,
    onBg: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(horizontal = 20.dp)
            .padding(top = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Good Morning, $greetingName",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.SemiBold
            ),
            color = onBg,
            modifier = Modifier.weight(1f),
            maxLines = 1
        )
    }
}

@Composable
private fun QuoteCard(
    surfaceCard: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.padding(horizontal = 20.dp),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = surfaceCard),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 18.dp, vertical = 14.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Winston Churchill:",
                fontFamily = dancingSemiBoldFont(),
                modifier = Modifier.fillMaxSize(),
                textAlign = TextAlign.Center,
                fontSize = 22.sp,
                color = Color(0xFF1F3B7A)
            )
            Spacer(Modifier.height(6.dp))
            Text(
                text = "\"Success is the ability to go from failure to failure without losing enthusiasm.\"",
                modifier = Modifier.fillMaxSize(),
                fontFamily = dancingSemiBoldFont(),
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                color = Color(0xFF1F3B7A)
            )
        }
    }
}

@Composable
private fun SectionHeader(
    title: String,
    subtitle: String,
    onBg: Color
) {
    Column(Modifier.padding(horizontal = 20.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
            color = onBg
        )
        Spacer(Modifier.height(2.dp))
        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodySmall,
            color = onBg.copy(alpha = 0.7f)
        )
    }
}

private fun sampleCategories(): List<CategoryItem> = listOf(
    CategoryItem("Cards", Res.drawable.im_cards),
    CategoryItem("Music", Res.drawable.im_music),
    CategoryItem("Video", Res.drawable.im_video),
    CategoryItem("Photo", Res.drawable.im_photo),
    CategoryItem("Book", Res.drawable.im_book)
)

@Immutable
data class CategoryItem(
    val label: String,
    val image: DrawableResource
)

@Composable
private fun CategoryRow(
    items: List<CategoryItem>,
    chipBg: Color,
    chipBorder: Color,
    onCategoryClick: (CategoryItem) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEach { item ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = chipBg,
                    modifier = Modifier
                        .size(width = 56.dp, height = 44.dp)
                        .border(1.dp, chipBorder, RoundedCornerShape(14.dp))
                        .clickable { onCategoryClick(item) }
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Image(
                            painter = painterResource(item.image),
                            contentDescription = item.label
                        )
                    }
                }

                Spacer(Modifier.height(6.dp))

                Text(
                    text = item.label,
                    style = MaterialTheme.typography.labelMedium,
                    color = Color(0xFF111827).copy(alpha = 0.9f)
                )
            }
        }
    }
}

@Immutable
data class MediaCard(
    val tag: String,
    val title: String,
    val subtitle: String,
    val imageUrl: String
)

@Composable
private fun CardRow(items: List<MediaCard>, with: Dp, height: Dp) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp)
    ) {
        items(items) { card ->
            MediaImageCard(
                card = card,
                modifier = Modifier.size(width = with, height = height)
            )
        }
    }
}

@Composable
private fun MediaImageCard(
    card: MediaCard,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(18.dp),
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Box {
            AsyncImage(
                model = card.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Black.copy(alpha = 0.05f),
                                Color.Black.copy(alpha = 0.55f)
                            )
                        )
                    )
            )

            Surface(
                color = Color.Black.copy(alpha = 0.35f),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .padding(12.dp)
                    .align(Alignment.TopStart)
            ) {
                Text(
                    text = card.tag,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                )
            }

            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(14.dp)
            ) {
                Text(
                    text = card.title,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = Color.White
                )
                if (card.subtitle.isNotBlank()) {
                    Spacer(Modifier.height(2.dp))
                    Text(
                        text = card.subtitle,
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.White.copy(alpha = 0.85f)
                    )
                }
            }
        }
    }
}

private fun samplePopular(): List<MediaCard> = listOf(
    MediaCard(
        tag = "Music",
        title = "Nature sounds",
        subtitle = "",
        imageUrl = "https://images.unsplash.com/photo-1469474968028-56623f02e42e?w=1200&auto=format&fit=crop"
    ),
    MediaCard(
        tag = "Card",
        title = "The day starts\nwith you",
        subtitle = "",
        imageUrl = "https://images.unsplash.com/photo-1500530855697-b586d89ba3ee?w=1200&auto=format&fit=crop"
    ),
    MediaCard(
        tag = "Gallery",
        title = "Breathe slowly",
        subtitle = "",
        imageUrl = "https://images.unsplash.com/photo-1501785888041-af3ef285b470?w=1200&auto=format&fit=crop"
    ),
    MediaCard(
        tag = "Video",
        title = "A video\nthat changes your mood",
        subtitle = "",
        imageUrl = "https://images.unsplash.com/photo-1469474968028-56623f02e42e?w=1200&auto=format&fit=crop"
    ),
)

private fun sampleTrending(): List<MediaCard> = listOf(
    MediaCard(
        tag = "Relax",
        title = "In the forest",
        subtitle = "Nature Sounds",
        imageUrl = "https://images.unsplash.com/photo-1441974231531-c6227db76b6e?w=1200&auto=format&fit=crop"
    ),
    MediaCard(
        tag = "Motivation",
        title = "Sunrise Energy",
        subtitle = "Energetic Morning",
        imageUrl = "https://images.unsplash.com/photo-1470770841072-f978cf4d019e?w=1200&auto=format&fit=crop"
    ),
    MediaCard(
        tag = "Motivation",
        title = "Sunrise Energy",
        subtitle = "Energetic Morning",
        imageUrl = "https://images.unsplash.com/photo-1470770841072-f978cf4d019e?w=1200&auto=format&fit=crop"
    ),
    MediaCard(
        tag = "Relax",
        title = "In the forest",
        subtitle = "Nature Sounds",
        imageUrl = "https://images.unsplash.com/photo-1441974231531-c6227db76b6e?w=1200&auto=format&fit=crop"
    )
)

@Preview
@Composable
private fun InspirationHomeScreenPreview() {
    MaterialTheme {
        InspirationHomeContent()
    }
}
