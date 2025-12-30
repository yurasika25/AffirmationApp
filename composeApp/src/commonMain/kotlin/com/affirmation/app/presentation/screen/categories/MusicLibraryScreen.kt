package com.affirmation.app.presentation.screen.categories

import affirmationapp.composeapp.generated.resources.Res
import affirmationapp.composeapp.generated.resources.im_back_btn
import affirmationapp.composeapp.generated.resources.search
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import coil3.compose.AsyncImage
import com.affirmation.app.presentation.screen.categories.helper.Category.categoryList
import com.affirmation.app.presentation.screen.categories.model.MusicCategoryModel
import com.affirmation.app.presentation.screen.categories.helper.heroMusicLibraryUrl
import com.affirmation.app.presentation.screen.player.AudioPlayerScreen
import com.affirmation.app.utils.HideBottomBar
import org.jetbrains.compose.resources.painterResource

class MusicScreen : Screen {
    @Composable
    override fun Content() {
        HideBottomBar()
        val navigator = LocalNavigator.current
        MusicLibraryScreen(
            onBackClick = { navigator?.pop() },
            onCategoryClick = { cat ->
                navigator?.push(AudioPlayerScreen(cat.imageUrl, cat.title, cat.audioUrl))
            }
        )
    }
}

@Composable
fun MusicLibraryScreen(
    onBackClick: () -> Unit,
    onCategoryClick: (MusicCategoryModel) -> Unit
) {
    val pageBg = Color(0xFFEFF5FF)
    val categories = remember { categoryList }

    Box(modifier = Modifier.fillMaxSize().background(pageBg)) {
        HeroHeaderBackground(onBackClick = onBackClick)

        ContentSheetFixedBottom(
            pageBg = pageBg,
            categories = categories,
            onCategoryClick = onCategoryClick
        )
    }
}

@Composable
private fun HeroHeaderBackground(onBackClick: () -> Unit) {

    Box(modifier = Modifier.fillMaxWidth().height(350.dp)) {
        AsyncImage(
            model = heroMusicLibraryUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Box(modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.25f)))

        // Back Button
        Surface(
            modifier = Modifier.statusBarsPadding().padding(start = 16.dp, top = 10.dp).size(44.dp)
                .clip(RoundedCornerShape(14.dp)).clickable { onBackClick() },
            color = Color.White.copy(alpha = 0.6f)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    painter = painterResource(Res.drawable.im_back_btn),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        Column(modifier = Modifier.align(Alignment.TopStart).padding(start = 20.dp, top = 140.dp)) {
            Text(
                text = "Music Library",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                ),
            )
            Spacer(Modifier.height(12.dp))

            Text("Explore your favorite music\nby category", color = Color.White.copy(alpha = 0.8f))
        }
    }
}

@Composable
private fun ContentSheetFixedBottom(
    pageBg: Color,
    categories: List<MusicCategoryModel>,
    onCategoryClick: (MusicCategoryModel) -> Unit
) {
    var query by remember { mutableStateOf("") }
    val sheetShape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .clip(sheetShape)
                .background(pageBg)
                .navigationBarsPadding()
                .padding(horizontal = 20.dp)
                .padding(top = 28.dp, bottom = 12.dp)
        ) {
            AnimatedSearchField(value = query, onValueChange = { query = it })

            Spacer(Modifier.height(24.dp))

            val rows = categories.chunked(2)
            rows.forEachIndexed { rowIndex, rowItems ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(18.dp)
                ) {
                    rowItems.forEach { category ->
                        Box(modifier = Modifier.weight(1f)) {
                            CategoryCard(
                                title = category.title,
                                subtitle = category.subtitle,
                                imageUrl = category.imageUrl,
                                onClick = { onCategoryClick(category) }
                            )
                        }
                    }
                }
                if (rowIndex < rows.size - 1) Spacer(Modifier.height(18.dp))
            }
        }
    }
}

@Composable
private fun AnimatedSearchField(value: String, onValueChange: (String) -> Unit) {
    val shape = RoundedCornerShape(18.dp)
    var focused by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = shape,
        color = if (focused) Color.White else Color(0xFFF7FAFF),
        shadowElevation = if (focused) 4.dp else 0.dp
    ) {
        Row(
            modifier = Modifier.height(56.dp)
                .border(1.dp, if (focused) Color(0xFF8DB2FF) else Color(0xFFD6E2F3), shape)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(Res.drawable.search),
                contentDescription = null,
                tint = Color(0xFF51719A),
                modifier = Modifier.size(20.dp)
            )
            Spacer(Modifier.width(12.dp))
            Box(modifier = Modifier.weight(1f)) {
                if (value.isEmpty()) Text("Search music...", color = Color(0xFF7B8DA6))
                BasicTextField(
                    value = value,
                    onValueChange = onValueChange,
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().onFocusChanged { },
                    textStyle = TextStyle(fontSize = MaterialTheme.typography.bodyLarge.fontSize)
                )
            }
        }
    }
}

@Composable
private fun CategoryCard(title: String, subtitle: String, imageUrl: String, onClick: () -> Unit) {
    val interaction = remember { MutableInteractionSource() }
    val pressed by interaction.collectIsPressedAsState()
    val scale by animateFloatAsState(if (pressed) 0.96f else 1f)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.85f)
            .graphicsLayer { scaleX = scale; scaleY = scale }
            .clip(RoundedCornerShape(24.dp))
            .clickable(interactionSource = interaction, indication = null, onClick = onClick)
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Box(
            modifier = Modifier.fillMaxSize().background(
                Brush.verticalGradient(
                    listOf(
                        Color.Transparent,
                        Color.Black.copy(alpha = 0.7f)
                    )
                )
            )
        )
        Column(modifier = Modifier.align(Alignment.BottomStart).padding(16.dp)) {
            Text(
                title,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                subtitle,
                color = Color.White.copy(alpha = 0.8f),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}