package com.affirmation.app.presentation.ui.screens

import AffirmationToolBar
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.affirmation.app.utils.HideBottomBar

class SettingScreen : Screen {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {

        HideBottomBar()

        val pageBgTop = Color(0xFFF7FAFF)
        val pageBgBottom = Color(0xFFEAF1FF)

        val navigator = LocalNavigator.currentOrThrow

        var notificationsEnabled by remember { mutableStateOf(true) }
        var affirmOnMainEnabled by remember { mutableStateOf(true) }

        var theme by remember { mutableStateOf(ThemeOption.Light) }
        var language by remember { mutableStateOf("English") }

        Scaffold(
            topBar = {
                AffirmationToolBar(
                    title = "Settings",
                    showBack = true,
                    onBackClick = { navigator.pop() }
                )
            }
        ) { padding ->

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Brush.verticalGradient(listOf(pageBgTop, pageBgBottom)))
                    .padding(padding),
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 18.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                item {
                    SectionHeader("App")
                    Spacer(Modifier.height(10.dp))
                    SettingsCard {
                        SettingsSwitchRow(
                            title = "Notifications",
                            checked = notificationsEnabled,
                            onCheckedChange = { }
                        )
                        DividerLine()
                        SettingsSwitchRow(
                            title = "Affirm on Main Page",
                            checked = affirmOnMainEnabled,
                            onCheckedChange = { }
                        )
                        DividerLine()
                        SettingsSegmentedRow(
                            title = "Theme",
                            options = ThemeOption.entries,
                            selected = theme,
                            onSelected = { }
                        )
                        DividerLine()
                        SettingsNavRow(
                            title = "Language",
                            value = language,
                            onClick = { navigator.push(LanguageScreen()) }

                        )
                    }
                }

                item {
                    SectionHeader("Help & Support")
                    Spacer(Modifier.height(10.dp))
                    SettingsCard {
                        SettingsNavRow(
                            title = "Contacts Us",
                            onClick = {
                                // navigator.push(ContactUsScreen())
                            }
                        )
                        DividerLine()
                        SettingsNavRow(
                            title = "FAQ",
                            onClick = {
                                // navigator.push(FaqScreen())
                            }
                        )
                    }
                }

                item {
                    SectionHeader("Legal Info")
                    Spacer(Modifier.height(10.dp))
                    SettingsCard {
                        SettingsNavRow(
                            title = "Terms of Use",
                            onClick = {
                                // navigator.push(TermsScreen())
                            }
                        )
                        DividerLine()
                        SettingsNavRow(
                            title = "Privacy Policy",
                            onClick = {
                                // navigator.push(PrivacyScreen())
                            }
                        )
                        DividerLine()
                        SettingsNavRow(
                            title = "Cookies Policy",
                            onClick = {
                                // navigator.push(CookiesScreen())
                            }
                        )
                    }
                }

                item {
                    SectionHeader("Account")
                    Spacer(Modifier.height(10.dp))
                    SettingsCard {
                        SettingsNavRow(
                            title = "Log Out",
                            onClick = {
                                // TODO logout
                            }
                        )
                    }
                }

                item {
                    Spacer(Modifier.height(10.dp))
                    TextButton(
                        onClick = {
                            // TODO delete account flow
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Delete Account",
                            color = Color(0xFFE53935),
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Spacer(Modifier.height(24.dp))
                }
            }
        }
    }
}


@Composable
private fun SectionHeader(title: String) {
    Text(
        text = title,
        fontSize = MaterialTheme.typography.titleLarge.fontSize,
        fontWeight = FontWeight.SemiBold,
        color = Color(0xFF111827)
    )
}

@Composable
private fun SettingsCard(content: @Composable ColumnScope.() -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        color = Color.White,
        tonalElevation = 0.dp,
        shadowElevation = 0.dp
    ) {
        Column(modifier = Modifier.fillMaxWidth(), content = content)
    }
}

@Composable
private fun DividerLine() {
    HorizontalDivider(
        thickness = 1.dp,
        color = Color(0xFFE8EEF9),
        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
    )
}

@Composable
private fun SettingsSwitchRow(
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 54.dp)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            color = Color(0xFF111827),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = Color(0xFF5A7CC8),
                uncheckedThumbColor = Color.White,
                uncheckedTrackColor = Color(0xFFD6DCEB)
            )
        )
    }
}

private enum class ThemeOption(val label: String) {
    Light("Light"),
    Dark("Dark"),
    Auto("Auto");

    companion object {
        val entries: List<ThemeOption> = listOf(Light, Dark, Auto)
    }
}

@Composable
private fun SettingsSegmentedRow(
    title: String,
    options: List<ThemeOption>,
    selected: ThemeOption,
    onSelected: (ThemeOption) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 54.dp)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            color = Color(0xFF111827),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )

        SegmentedControl(
            options = options,
            selected = selected,
            onSelected = onSelected
        )
    }
}

@Composable
private fun SegmentedControl(
    options: List<ThemeOption>,
    selected: ThemeOption,
    onSelected: (ThemeOption) -> Unit
) {
    val containerBg = Color(0xFFEAF1FF)
    val selectedBg = Color(0xFF5A7CC8)
    val unselectedText = Color(0xFF111827)
    val selectedText = Color.White

    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(containerBg)
            .padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        options.forEach { opt ->
            val isSelected = opt == selected
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(if (isSelected) selectedBg else Color.Transparent)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) { onSelected(opt) }
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = opt.label,
                    color = if (isSelected) selectedText else unselectedText,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                )
            }
        }
    }
}

@Composable
private fun SettingsNavRow(
    title: String,
    value: String? = null,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 54.dp)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { onClick() }
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            color = Color(0xFF111827),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )

        if (value != null) {
            Text(
                text = value,
                color = Color(0xFF6B7280),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(end = 8.dp)
            )
        }

        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = Color(0xFF9CA3AF)
        )
    }
}
