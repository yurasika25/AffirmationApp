package com.affirmation.app.presentation.ui.screens

import affirmationapp.composeapp.generated.resources.Res
import affirmationapp.composeapp.generated.resources.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.affirmation.app.utils.items
import com.affirmation.app.utils.monthNames
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

private val LavenderBg = Color(0xFFF5F1FB)
private val CardScrimTop = Color(0x66000000)
private val CardScrimBottom = Color(0xCC000000)
private val ChipBg = Color(0xFFE9E1FF)
private val TextSecondary = Color(0xFF6B6B7A)

class HomeScreen : Screen {

    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    override fun Content() {

        val focus = LocalFocusManager.current
        val keyboard = LocalSoftwareKeyboardController.current
        val navigator = LocalNavigator.currentOrThrow

        val todayFormatted = remember {
            val currentDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            val monthName = monthNames[currentDateTime.monthNumber - 1]
            "$monthName ${currentDateTime.dayOfMonth}, ${currentDateTime.year}"
        }

        val data = items
        val hero = data.firstOrNull()

        Scaffold(
            containerColor = LavenderBg,
            topBar = {
                HomeTopBar(
                    username = "Dev",
                    date = "Today is $todayFormatted"
                )
            },
        ) { innerPadding ->

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {

                item {
                    var q by remember { mutableStateOf("") }
                    Surface(
                        color = Color.White,
                        shape = RoundedCornerShape(18.dp),
                        tonalElevation = 0.dp,
                        shadowElevation = 0.dp,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        TextField(
                            value = q,
                            onValueChange = { q = it },
                            placeholder = { Text("Enter keywords to search for") },
                            singleLine = true,
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(Res.drawable.search),
                                    contentDescription = "Search icon",
                                    modifier = Modifier.size(24.dp)
                                )
                            },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,
                                disabledContainerColor = Color.White,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                            keyboardActions = KeyboardActions(
                                onSearch = {
                                    focus.clearFocus(force = true)
                                    keyboard?.hide()
                                }
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                item {
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        listOf("#Self-development", "#Selflove", "#Motivation").forEach { label ->
                            PillChip(label)
                        }
                    }
                }

                if (hero != null) {
                    item {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                "Affirmation of the day",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
                            )
                            Spacer(Modifier.weight(1f))
                            Icon(
                                painter = painterResource(Res.drawable.refresh),
                                contentDescription = "Refresh icon",
                                modifier = Modifier
                                    .padding(end = 11.dp)
                                    .size(16.dp)
                                    .clickable { /* TODO refresh */ }
                            )
                        }
                    }

                    item {
                        HeroAffirmationCard(
                            image = hero.icon,
                            title = "BELIEVE IN YOURSELF",
                            tags = listOf("#Self-develop", "#Selflove", "#Motivation"),
                            onClick = {
                                navigator.push(
                                    AffirmationDetailsScreen(
                                        image = hero.icon,
                                        bannerTitle = hero.text,
                                        mainText = hero.subtitle
                                    )
                                )
                            }
                        )
                    }
                }

                if (data.size > 1) {
                    item {
                        Text(
                            "Other affirmations",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
                        )
                    }

                    items(data.drop(1)) { item ->
                        OtherAffirmationCard(
                            image = item.icon,
                            title = item.text,
                            tag = "#Selflove",
                            onClick = {
                                navigator.push(
                                    AffirmationDetailsScreen(
                                        image = item.icon,
                                        bannerTitle = item.text,
                                        mainText = item.subtitle
                                    )
                                )
                            })
                    }
                }

                item { Spacer(Modifier.height(8.dp)) }
            }
        }
    }
}

@Composable
private fun HomeTopBar(
    username: String,
    date: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .statusBarsPadding()
    ) {
        Column {
            Text(
                text = "Hi, $username!",
                fontSize = 26.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
            Text(
                text = date,
                fontSize = 14.sp,
                color = TextSecondary,
                modifier = Modifier.padding(top = 6.dp)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Card(
            shape = CircleShape,
            colors = CardDefaults.cardColors(containerColor = Color.LightGray),
            elevation = CardDefaults.cardElevation(0.dp),
            modifier = Modifier
                .size(48.dp)
        ) {
            Image(
                painter = painterResource(Res.drawable.im_avatar),
                contentDescription = "Avatar",
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
private fun PillChip(text: String) {
    Surface(color = ChipBg, shape = RoundedCornerShape(16.dp)) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 6.dp),
            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Medium)
        )
    }
}

@Composable
private fun HeroAffirmationCard(
    image: DrawableResource,
    title: String,
    tags: List<String>,
    onClick: () -> Unit
) {


    val navigator = LocalNavigator.currentOrThrow

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .clip(RoundedCornerShape(28.dp))
            .clickable(onClick = onClick)
    ) {
        Image(
            painter = painterResource(image),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Box(
            Modifier
                .matchParentSize()
                .background(
                    Brush.verticalGradient(
                        0f to CardScrimTop,
                        0.55f to Color.Transparent,
                        1f to CardScrimBottom
                    )
                )
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(12.dp)
        ) {
            BubbleIcon(painterResource(Res.drawable.play), onClick = {
                navigator.push(
                    PlayerScreen(
                        image = image,
                        title = title,
                    )
                )
            })
            BubbleIcon(painterResource(Res.drawable.like)) { /* like */ }
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(18.dp)
        ) {
            Text(
                title,
                color = Color.White,
                fontSize = 26.sp,
                fontWeight = FontWeight.ExtraBold,
                maxLines = 2
            )
            Spacer(Modifier.height(10.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    "Read more",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(Modifier.width(6.dp))
                Icon(
                    painter = painterResource(Res.drawable.arrow_up),
                    contentDescription = "Arrow-up icon",
                    modifier = Modifier.size(20.dp),
                    tint = Color.White
                )
            }
            Spacer(Modifier.height(10.dp))
            FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                tags.forEach { SmallGlassChip(it) }
            }
        }
    }
}

@Composable
private fun OtherAffirmationCard(
    image: DrawableResource,
    title: String,
    tag: String,
    onClick: () -> Unit,
) {

    val navigator = LocalNavigator.currentOrThrow

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp)
            .clip(RoundedCornerShape(22.dp))
            .clickable(onClick = onClick)
    ) {
        Image(
            painter = painterResource(image),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Box(
            Modifier
                .matchParentSize()
                .background(
                    Brush.verticalGradient(
                        0f to CardScrimTop.copy(alpha = .35f),
                        1f to CardScrimBottom.copy(alpha = .55f)
                    )
                )
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(10.dp)
        ) {
            BubbleIcon(painterResource(Res.drawable.play), onClick = {
                navigator.push(
                    PlayerScreen(
                        image = image,
                        title = title,
                    )
                )
            })


            BubbleIcon(painterResource(Res.drawable.like)) {}
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(14.dp)
        ) {
            Text(
                title,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                SmallGlassChip(tag)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Read more", color = Color.White)
                    Spacer(Modifier.width(6.dp))
                    Icon(
                        painter = painterResource(Res.drawable.arrow_up),
                        contentDescription = "Arrow-up icon",
                        modifier = Modifier.size(20.dp),
                        tint = Color.White
                    )
                }
            }
        }
    }
}

@Composable
private fun SmallGlassChip(text: String) {
    Surface(
        color = Color(0x55FFFFFF),
        contentColor = Color.White,
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(
            text,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Composable
private fun BubbleIcon(icon: Painter, onClick: () -> Unit) {
    Surface(
        color = Color(0x33000000),
        shape = CircleShape
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clickable(onClick = onClick),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = icon,
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}