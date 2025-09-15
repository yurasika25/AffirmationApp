package com.affirmation.app.presentation.ui.screens

import affirmationapp.composeapp.generated.resources.Res
import affirmationapp.composeapp.generated.resources.im_me
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.affirmation.app.utils.GlobalTopBar
import org.jetbrains.compose.resources.painterResource

class ProfileScreen(
    private val onSignOut: () -> Unit = { /* TODO: clear session + navigate to auth */ }
) : Screen {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow

        val pageBg = Color(0xFFFAF7FF)
        val accent = Color(0xFF6A5AE0)
        var pushEnabled by rememberSaveable { mutableStateOf(true) }

        var showExitDialog by rememberSaveable { mutableStateOf(false) }

        Scaffold(
            containerColor = pageBg,
            topBar = { GlobalTopBar("Profile") },
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 28.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {

                item {
                    ProfileHeaderCard(
                        name = "Oksana",
                        email = "oksana@gmail.com",
                        bio = "I love traveling, books, and morning coffee. I strive to become a better version of myself every day.",
                        daysActive = 127,
                        readCount = 342,
                        createdCount = 5,
                        accent = accent
                    )
                }

                item { SectionTitle("Settings") }
                item {
                    SectionCard {
                        SettingToggleRow(
                            leadingGlyph = "🔔",
                            title = "Push Notifications",
                            subtitle = "Daily reminder for affirmations.",
                            checked = pushEnabled,
                            onCheckedChange = { pushEnabled = it }
                        )
                        Divider(color = Color(0x1A000000))
                        SettingNavRow(
                            leadingGlyph = "🎨",
                            title = "Theme",
                            subtitle = "App appearance.",
                            onClick = { /* TODO */ }
                        )
                        Divider(color = Color(0x1A000000))
                        SettingNavRow(
                            leadingGlyph = "🌐",
                            title = "Language",
                            subtitle = "App language.",
                            onClick = { /* TODO */ }
                        )
                    }
                }

                item { SectionTitle("Additional information and support") }
                item {
                    SectionCard {
                        SettingNavRow(
                            leadingGlyph = "🆘",
                            title = "Help&Support",
                            subtitle = "Describe your problem to us and we will help you solve it.",
                            onClick = { navigator.push(SupportHelpScreen()) }
                        )
                        Divider(color = Color(0x1A000000))
                        SettingNavRow(
                            leadingGlyph = "✉️",
                            title = "Send Feedback",
                            subtitle = "Write us your opinion so that we can improve.",
                            onClick = { navigator.push(SendFeedbackScreen()) }
                        )
                    }
                }

                item {
                    Surface(
                        shape = RoundedCornerShape(18.dp),
                        color = Color.White,
                        border = BorderStroke(1.dp, Color(0x1A000000)),
                        tonalElevation = 0.dp,
                        shadowElevation = 0.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { showExitDialog = true }
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 18.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(Color(0x14FF3B30)),
                                contentAlignment = Alignment.Center
                            ) { Text("🚪", fontSize = 18.sp) }

                            Spacer(Modifier.width(12.dp))
                            Text(
                                "Sign Out",
                                color = Color(0xFFFF3B30),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }

                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Version 1.0.0", color = Color(0xFFB9B4C9), fontSize = 12.sp)
                    }
                }
            }
        }

        if (showExitDialog) {
            ConfirmExitDialog(
                title = "Exit",
                message = "Are you sure you want to exit?",
                cancelText = "Cancel",
                confirmText = "Exit",
                onCancel = { showExitDialog = false },
                onConfirm = {
                    showExitDialog = false
                    onSignOut()
                }
            )
        }
    }
}

/* ---------------------- Dialog ---------------------- */

@Composable
private fun ConfirmExitDialog(
    title: String,
    message: String,
    cancelText: String,
    confirmText: String,
    onCancel: () -> Unit,
    onConfirm: () -> Unit
) {
    Dialog(onDismissRequest = onCancel) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            tonalElevation = 0.dp,
            shadowElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .widthIn(min = 280.dp)
                    .padding(top = 18.dp)
            ) {
                Text(
                    title,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    color = Color(0xFF1C1B1F)
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    message,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    textAlign = TextAlign.Center,
                    color = Color(0xFF8A8A8F)
                )

                Spacer(Modifier.height(14.dp))
                Divider(color = Color(0x14000000))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .clickable(onClick = onCancel),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(cancelText, fontSize = 16.sp, color = Color(0xFF1C1B1F))
                    }

                    Box(
                        modifier = Modifier
                            .width(1.dp)
                            .fillMaxHeight()
                            .background(Color(0x14000000))
                    )

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .clickable(onClick = onConfirm),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(confirmText, fontSize = 16.sp, color = Color(0xFFFF3B30))
                    }
                }
            }
        }
    }
}


@Composable
private fun ProfileHeaderCard(
    name: String,
    email: String,
    bio: String,
    daysActive: Int,
    readCount: Int,
    createdCount: Int,
    accent: Color
) {
    Surface(
        shape = RoundedCornerShape(18.dp),
        color = Color.White,
        tonalElevation = 0.dp,
        shadowElevation = 0.dp,
        border = BorderStroke(1.dp, Color(0x1A000000)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFEDE6FF)),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(Res.drawable.im_me),
                        contentDescription = "avatar",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Spacer(Modifier.width(12.dp))
                Column(Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(name, fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
                        Spacer(Modifier.width(6.dp))
                        Text("✏️", fontSize = 14.sp)
                    }
                    Spacer(Modifier.height(8.dp))
                    Text(
                        bio,
                        color = Color(0xFF6F6A83),
                        fontSize = 14.sp,
                        lineHeight = 18.sp,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(email, color = Color(0xFF8D89A0), fontSize = 13.sp)
                }
            }

            Spacer(Modifier.height(12.dp))
            Divider(color = Color(0x1A000000))
            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatItem("Days active", daysActive.toString(), accent)
                StatItem("Affirmations read", readCount.toString(), accent)
                StatItem("Affirmations created", createdCount.toString(), accent)
            }
        }
    }
}

@Composable
private fun StatItem(title: String, value: String, accent: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth(0.25f)) {
        Text(value, color = accent, fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
        Spacer(Modifier.height(4.dp))
        Text(title, color = Color(0xFF8D89A0), fontSize = 12.sp, lineHeight = 14.sp)
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        text,
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        color = Color(0xFF3A2F57),
        modifier = Modifier.padding(start = 4.dp)
    )
}

@Composable
private fun SectionCard(content: @Composable ColumnScope.() -> Unit) {
    Surface(
        shape = RoundedCornerShape(18.dp),
        color = Color.White,
        border = BorderStroke(1.dp, Color(0x1A000000)),
        tonalElevation = 0.dp,
        shadowElevation = 0.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(Modifier.padding(horizontal = 12.dp, vertical = 6.dp)) { content() }
    }
}

@Composable
private fun SettingToggleRow(
    leadingGlyph: String,
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconTile(leadingGlyph)
        Spacer(Modifier.width(10.dp))
        Column(Modifier.weight(1f)) {
            Text(title, fontWeight = FontWeight.Medium, fontSize = 16.sp)
            Spacer(Modifier.height(2.dp))
            Text(subtitle, color = Color(0xFF8D89A0), fontSize = 13.sp)
        }
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}

@Composable
private fun SettingNavRow(
    leadingGlyph: String,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconTile(leadingGlyph)
        Spacer(Modifier.width(10.dp))
        Column(Modifier.weight(1f)) {
            Text(title, fontWeight = FontWeight.Medium, fontSize = 16.sp)
            Spacer(Modifier.height(2.dp))
            Text(subtitle, color = Color(0xFF8D89A0), fontSize = 13.sp)
        }
        Text("›", color = Color(0xFF8D89A0), fontSize = 20.sp)
    }
}

@Composable
private fun IconTile(glyph: String) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFF4EEFF)),
        contentAlignment = Alignment.Center
    ) { Text(glyph, fontSize = 18.sp) }
}
