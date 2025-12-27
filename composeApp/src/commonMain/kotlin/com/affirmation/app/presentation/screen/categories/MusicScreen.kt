package com.affirmation.app.presentation.screen.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import org.jetbrains.compose.resources.painterResource
import affirmationapp.composeapp.generated.resources.Res
import affirmationapp.composeapp.generated.resources.im_back_btn
import affirmationapp.composeapp.generated.resources.search
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import com.affirmation.app.presentation.screen.player.MusicPlayerScreen
import com.affirmation.app.utils.HideBottomBar

@Immutable
data class MusicCategory(
    val id: String,
    val title: String,
    val subtitle: String,
    val imageUrl: String,
    val imageBlurUrl: String
)

class MusicScreen() : Screen {
    @Composable
    override fun Content() {
        HideBottomBar()

        val navigator = LocalNavigator.current

        MusicLibraryScreen(
            navigator,
            onBackClick = { navigator?.pop() },
            onSearchChange = {},
            onCategoryClick = {}
        )
    }
}

@Composable
fun MusicLibraryScreen(
    navigator: Navigator?,
    onBackClick: () -> Unit = {},
    onSearchChange: (String) -> Unit = {},
    onCategoryClick: (MusicCategory) -> Unit = {}
) {
    val headerHeight = 320.dp
    val sheetTop = 265.dp //

    val pageBg = Color(0xFFEFF5FF)
    val sheetShape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)

    val categories = remember {
        listOf(
            MusicCategory(
                "sleep",
                "Sleep & Dream",
                "Relax and drift away",
                "https://res.cloudinary.com/dkbbgpfcl/image/upload/v1766805789/pexels-brett-sayles-3910141_2_gqicrd.jpg",
                "https://res.cloudinary.com/dkbbgpfcl/image/upload/v1766854090/relax_blur_oyvpzi.jpg"
            ),
            MusicCategory(
                "morning",
                "Morning Energy",
                "Start your day with positivity",
                "https://res.cloudinary.com/dkbbgpfcl/image/upload/v1766803506/yana-gorbunova-r3Kpb5X7Ep8-unsplash_fyyrkl.jpg",
                "https://res.cloudinary.com/dkbbgpfcl/image/upload/v1766854089/morning_blur_xajq1y.jpg"
            ),
            MusicCategory(
                "focus",
                "Focus & Power",
                "Push your limits",
                "https://res.cloudinary.com/dkbbgpfcl/image/upload/v1766804917/pexels-njeromin-15364788_lfkzi5.jpg",
                "https://res.cloudinary.com/dkbbgpfcl/image/upload/v1766854089/nature_blur_vfnfgb.jpg"
            ),
            MusicCategory(
                "nature",
                "Nature Sounds",
                "Connect with the earth",
                "https://res.cloudinary.com/dkbbgpfcl/image/upload/v1766853041/fire_new_xaelw7.jpg",
                "https://res.cloudinary.com/dkbbgpfcl/image/upload/v1766854089/focus_blur_mqssb9.jpg"
            )
        )
    }

    var query by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize().background(pageBg)
    ) {

        Box(
            modifier = Modifier.fillMaxWidth().height(headerHeight)
        ) {
            AsyncImage(
                model = "https://res.cloudinary.com/dkbbgpfcl/image/upload/v1766805936/pexels-kwsignatureseries-89909_xcihgu.jpg",
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // Slight dark scrim for readable text
            Box(
                modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.18f))
            )

            // Smooth fade from photo to page background (like your 2nd screen)
            Box(
                modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth().height(100.dp)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, pageBg)
                        )
                    )
            )

            // Back button
            Box(
                modifier = Modifier.padding(start = 16.dp, top = 42.dp).size(44.dp)
                    .clip(RoundedCornerShape(12.dp)).background(Color.White.copy(alpha = 0.6f))
                    .clickable(onClick = onBackClick), contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(Res.drawable.im_back_btn),
                    contentDescription = "Back",
                    tint = Color(0xFF1B2430),
                    modifier = Modifier.size(20.dp)
                )
            }

            Column(
                modifier = Modifier.align(Alignment.BottomStart)
                    .padding(start = 20.dp, end = 20.dp, bottom = 110.dp)
            ) {
                Text(
                    text = "Music Library", style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.SemiBold, color = Color.White
                    )
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "Explore your favorite music\nby category",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = Color.White.copy(alpha = 0.9f)
                    )
                )
            }
        }

        Box(
            modifier = Modifier.fillMaxSize().padding(top = sheetTop).clip(sheetShape)
                .background(pageBg)
        ) {
            Column(
                modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp)
            ) {
                Spacer(Modifier.height(18.dp))

                SearchField(
                    value = query, onValueChange = {
                        query = it
                        onSearchChange(it)
                    })

                Spacer(Modifier.height(18.dp))

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    verticalArrangement = Arrangement.spacedBy(18.dp),
                    horizontalArrangement = Arrangement.spacedBy(22.dp)
                ) {
                    items(categories) {
                        CategoryCard(
                            title = it.title,
                            subtitle = it.subtitle,
                            imageUrl = it.imageUrl,
                            onClick = {
                                navigator?.push(MusicPlayerScreen(it.imageUrl, it.title))
                            })
                    }
                }
            }
        }
    }
}

@Composable
private fun SearchField(
    value: String, onValueChange: (String) -> Unit
) {
    val shape = RoundedCornerShape(16.dp)
    val borderColor = Color(0xFFD6E2F3)

    Row(
        modifier = Modifier.fillMaxWidth().height(54.dp).clip(shape).background(Color(0xFFF7FAFF))
            .border(1.dp, borderColor, shape).padding(horizontal = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(Res.drawable.search),
            contentDescription = null,
            tint = Color(0xFF51719A),
            modifier = Modifier.size(20.dp)
        )
        Spacer(Modifier.width(10.dp))

        Box(modifier = Modifier.fillMaxWidth()) {
            if (value.isEmpty()) {
                Text(
                    text = "What do you want to listen?",
                    color = Color(0xFF7B8DA6),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                singleLine = true,
                textStyle = TextStyle(
                    color = Color(0xFF1B2430),
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun CategoryCard(
    title: String, subtitle: String, imageUrl: String, onClick: () -> Unit
) {
    val shape = RoundedCornerShape(26.dp)

    Box(
        modifier = Modifier.fillMaxWidth().aspectRatio(0.82f).clip(shape)
            .clickable(onClick = onClick)
    ) {

        AsyncImage(
            model = imageUrl,
            contentDescription = title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier.align(Alignment.BottomStart).padding(18.dp)
        ) {
            Text(
                text = title, style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold, color = Color.White
                )
            )

            Spacer(Modifier.height(2.dp))

            Text(
                text = subtitle, style = MaterialTheme.typography.bodySmall.copy(
                    color = Color.White.copy(alpha = 0.9f)
                )
            )
        }
    }
}
