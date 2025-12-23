@file:OptIn(ExperimentalMaterial3Api::class)

package com.affirmation.app.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material.icons.outlined.CreditCard
import androidx.compose.material.icons.outlined.Headphones
import androidx.compose.material.icons.outlined.Photo
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.SmartDisplay
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import coil3.compose.AsyncImage
import org.jetbrains.compose.ui.tooling.preview.Preview

class InspirationHomeScreen : Screen {
    @Composable
    override fun Content() {
        InspirationHomeContent()
    }
}

@Composable
fun InspirationHomeContent(
    modifier: Modifier = Modifier,
    greetingName: String = "Iren",
    avatarUrl: String = "https://images.unsplash.com/photo-1544005313-94ddf0286df2?w=256&auto=format&fit=crop",
    heroImageUrl: String = "https://images.unsplash.com/photo-1501785888041-af3ef285b470?w=1600&auto=format&fit=crop",
) {
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
        // Hero image
        AsyncImage(
            model = heroImageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp)
        )

        // Fade from image into background
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
            contentPadding = PaddingValues(bottom = 116.dp)
        ) {
            item { Spacer(Modifier.height(8.dp)) }

            item {
                TopBar(
                    greetingName = greetingName,
                    avatarUrl = avatarUrl,
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

//            item { Spacer(Modifier.height(14.dp)) }

//            item {
//                SearchBar(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(horizontal = 20.dp)
//                )
//            }

            item { Spacer(Modifier.height(18.dp)) }

            item {
                SectionHeader(
                    title = "Categories",
                    subtitle = "That which most inspires category",
                    onBg = onBg
                )
            }

            item { Spacer(Modifier.height(12.dp)) }

            item {
                CategoryRow(
                    items = categories,
                    chipBg = chipBg,
                    chipBorder = chipBorder
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

            item { PopularRow(items = popular) }

            item { Spacer(Modifier.height(18.dp)) }

            item {
                SectionHeader(
                    title = "Trending",
                    subtitle = "That which trend today",
                    onBg = onBg
                )
            }

            item { Spacer(Modifier.height(12.dp)) }

            item {
                TrendingGridNonLazy(
                    items = trending
                )
            }
        }
    }
}

@Composable
private fun TopBar(
    greetingName: String,
    avatarUrl: String,
    onBg: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(horizontal = 20.dp)
            .padding(top = 8.dp),
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

        AsyncImage(
            model = avatarUrl,
            contentDescription = "Avatar",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .border(2.dp, Color.White.copy(alpha = 0.85f), CircleShape)
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
                text = "Winston Churchill",
                modifier = Modifier.fillMaxSize(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Medium,
                    fontStyle = FontStyle.Italic
                ),
                color = Color(0xFF1F3B7A)
            )
            Spacer(Modifier.height(6.dp))
            Text(
                text = "“Success is the ability to go from failure to\nfailure without losing enthusiasm.”",
                modifier = Modifier.fillMaxSize(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium.copy(fontStyle = FontStyle.Italic),
                color = Color(0xFF1F3B7A)
            )
        }
    }
}

@Composable
private fun SearchBar(
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = "",
        onValueChange = { },
        placeholder = { Text("Search") },
        leadingIcon = { Icon(Icons.Outlined.Search, contentDescription = null) },
        singleLine = true,
        shape = RoundedCornerShape(14.dp),
        modifier = modifier,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = Color.White.copy(alpha = 0.85f),
            focusedContainerColor = Color.White.copy(alpha = 0.95f),
            unfocusedBorderColor = Color(0xFFE4E8F5),
            focusedBorderColor = Color(0xFFD7DDF0)
        )
    )
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

@Immutable
data class CategoryItem(
    val label: String,
    val icon: @Composable () -> Unit
)

@Composable
private fun CategoryRow(
    items: List<CategoryItem>,
    chipBg: Color,
    chipBorder: Color
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp)
    ) {
        items(items) { item ->
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = chipBg,
                    modifier = Modifier
                        .size(width = 56.dp, height = 44.dp)
                        .border(1.dp, chipBorder, RoundedCornerShape(14.dp))
                ) {
                    Box(contentAlignment = Alignment.Center) { item.icon() }
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
private fun PopularRow(items: List<MediaCard>) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp)
    ) {
        items(items) { card ->
            MediaImageCard(
                card = card,
                modifier = Modifier.size(width = 190.dp, height = 140.dp)
            )
        }
    }
}

@Composable
private fun TrendingGridNonLazy(items: List<MediaCard>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        items.chunked(2).forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                rowItems.forEach { card ->
                    MediaImageCard(
                        card = card,
                        modifier = Modifier
                            .weight(1f)
                            .height(120.dp)
                    )
                }
                if (rowItems.size == 1) Spacer(Modifier.weight(1f))
            }
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

private fun sampleCategories(): List<CategoryItem> = listOf(
    CategoryItem("Cards") { Icon(Icons.Outlined.CreditCard, contentDescription = null) },
    CategoryItem("Music") { Icon(Icons.Outlined.Headphones, contentDescription = null) },
    CategoryItem("Video") { Icon(Icons.Outlined.SmartDisplay, contentDescription = null) },
    CategoryItem("Photo") { Icon(Icons.Outlined.Photo, contentDescription = null) },
    CategoryItem("Book") { Icon(Icons.AutoMirrored.Outlined.MenuBook, contentDescription = null) }
)

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
        tag = "Photo",
        title = "Breathe slowly",
        subtitle = "",
        imageUrl = "https://images.unsplash.com/photo-1501785888041-af3ef285b470?w=1200&auto=format&fit=crop"
    )
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
