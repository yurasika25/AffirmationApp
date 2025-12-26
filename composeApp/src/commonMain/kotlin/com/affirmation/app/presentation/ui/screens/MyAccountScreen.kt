package com.affirmation.app.presentation.ui.screens

import AffirmationToolBar
import affirmationapp.composeapp.generated.resources.Res
import affirmationapp.composeapp.generated.resources.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.affirmation.app.domain.model.UserProfileModel
import com.affirmation.app.utils.theme.dancingRegularFont
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

class ProfileScreen() : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        val user = remember {
            UserProfileModel(
                firstName = "User",
                lastName = "",
                gender = "",
                age = "",
                phoneNumber = "",
                email = "test.email@outlook.com"
            )
        }

        val pageBgTop = Color(0xFFF7FAFF)
        val pageBgBottom = Color(0xFFEAF1FF)

        val primaryText = Color(0xFF111827)
        val secondaryText = Color(0xFF6B7280)

        val border = Color(0x14000000)
        val iconTint = Color(0xFF1651AE)

        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                AffirmationToolBar(
                    title = "My Account",
                    showSettings = true,
                    onSettingsClick = { navigator.push(SettingScreen()) }
                )
            }
        ) { innerPadding ->

            val topOnly = PaddingValues(top = innerPadding.calculateTopPadding())

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Brush.verticalGradient(listOf(pageBgTop, pageBgBottom)))
                    .padding(topOnly)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 20.dp)
                        .padding(bottom = 120.dp)
                ) {

                    Spacer(Modifier.height(6.dp))

                    Text(
                        text = "Personal settings & activity",
                        color = secondaryText,
                        fontSize = 14.sp
                    )

                    Spacer(Modifier.height(16.dp))

                    AccountCard(
                        name = user.firstName.toString(),
                        email = user.email,
                        border = border,
                        onEdit = {
                            navigator.push(
                                EditProfileScreen(
                                    firstNameArg = user.firstName.orEmpty(),
                                    lastNameArg = user.lastName,
                                    ageArg = user.age,
                                    phoneArg = user.phoneNumber,
                                    emailArg = user.email,
                                    genderArg = user.gender
                                )
                            )
                        }
                    )

                    Spacer(Modifier.height(22.dp))

                    SectionHeader(
                        title = "My Activity",
                        subtitle = "Your recent activity at a glance",
                        primaryText = primaryText,
                        secondaryText = secondaryText
                    )

                    Spacer(Modifier.height(14.dp))

                    ActivityGrid(
                        border = border,
                        items = listOf(
                            ActivityItem(
                                icon = Res.drawable.im_cards,
                                labelTop = "12 viewed",
                                labelBottom = "Affirmation"
                            ),
                            ActivityItem(
                                icon = Res.drawable.im_photo,
                                labelTop = "8 saved",
                                labelBottom = "Gallery"
                            ),
                            ActivityItem(
                                icon = Res.drawable.im_music,
                                labelTop = "6 liked",
                                labelBottom = "Audio"
                            ),
                            ActivityItem(
                                icon = Res.drawable.im_video,
                                labelTop = "15 watched",
                                labelBottom = "Videos"
                            )
                        )
                    )

                    Spacer(Modifier.height(22.dp))

                    SectionHeader(
                        title = "My Affirmation",
                        subtitle = "Your personal power phrase",
                        primaryText = primaryText,
                        secondaryText = secondaryText
                    )

                    Spacer(Modifier.height(12.dp))

                    AffirmationCard(
                        text = "\"Success is the ability to go from failure to failure without losing enthusiasm.\"",
                        onEdit = {
                            // TODO: open edit affirmation screen/dialog
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun SectionHeader(
    title: String,
    subtitle: String,
    primaryText: Color,
    secondaryText: Color
) {
    Text(
        text = title,
        color = primaryText,
        fontSize = 20.sp,
        fontWeight = FontWeight.SemiBold
    )
    Spacer(Modifier.height(4.dp))
    Text(
        text = subtitle,
        color = secondaryText,
        fontSize = 14.sp
    )
}

@Composable
private fun AccountCard(
    name: String,
    email: String,
    border: Color,
    onEdit: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = Color.White.copy(alpha = 0.92f),
        tonalElevation = 0.dp,
        shadowElevation = 0.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(Modifier.padding(16.dp)) {

            Surface(
                shape = RoundedCornerShape(8.dp),
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(44.dp)
                    .clickable(onClick = onEdit)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        painter = painterResource(Res.drawable.edit_filled), // replace if needed
                        contentDescription = "Edit profile",
                        tint = Color(0xFF1651AE),
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, bottom = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Surface(
                    shape = CircleShape,
                    color = Color(0xFFEAF1FF),
                    modifier = Modifier.size(84.dp)
                ) {
                    Image(
                        painter = painterResource(Res.drawable.im_avatar), // replace if needed
                        contentDescription = "Avatar",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                    )
                }

                Spacer(Modifier.height(14.dp))

                Text(
                    text = name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF111827),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = email,
                    fontSize = 14.sp,
                    color = Color(0xFF6B7280),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
private fun AffirmationCard(
    text: String,
    onEdit: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = Color.White.copy(alpha = 0.92f),
        tonalElevation = 0.dp,
        shadowElevation = 0.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(Modifier.padding(1.dp)) {

            Surface(
                shape = RoundedCornerShape(14.dp),
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(44.dp)
                    .clickable(onClick = onEdit)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        painter = painterResource(Res.drawable.edit_filled),
                        contentDescription = "Edit affirmation",
                        tint = Color(0xFF1651AE),
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Text(
                text = text,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(vertical = 36.dp),
                textAlign = TextAlign.Center,
                fontFamily = dancingRegularFont(),
                fontSize = 20.sp,
                color = Color(0xFF1F3B7A)
            )
        }
    }
}

private data class ActivityItem(
    val icon: DrawableResource,
    val labelTop: String,
    val labelBottom: String
)

@Composable
private fun ActivityGrid(
    border: Color,
    items: List<ActivityItem>
) {
    val rows = items.chunked(2)

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        rows.forEach { row ->
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                row.forEach { item ->
                    ActivityTile(item, border, Modifier.weight(1f))
                }
                if (row.size == 1) Spacer(Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun ActivityTile(
    item: ActivityItem,
    border: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = Color.White.copy(alpha = 0.92f),
        modifier = modifier.heightIn(min = 135.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color(0xFFF2F6FF),
                modifier = Modifier.size(58.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        painter = painterResource(item.icon),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier.size(26.dp)
                    )
                }
            }

            Spacer(Modifier.height(12.dp))

            Text(
                text = item.labelTop,
                fontSize = 12.sp,
                color = Color(0xFF6B7280),
                lineHeight = 14.sp,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(2.dp))

            Text(
                text = item.labelBottom,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF111827),
                textAlign = TextAlign.Center,
                lineHeight = 18.sp
            )
        }
    }
}