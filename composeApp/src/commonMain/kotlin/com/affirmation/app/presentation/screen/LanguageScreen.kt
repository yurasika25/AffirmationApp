package com.affirmation.app.presentation.screen

import AffirmationToolBar
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.affirmation.app.utils.HideBottomBar

class LanguageScreen() : Screen {

    @Composable
    override fun Content() {

        val initialLanguage = "English"
        HideBottomBar()

        val pageBgTop = Color(0xFFF7FAFF)
        val pageBgBottom = Color(0xFFEAF1FF)

        val navigator = LocalNavigator.currentOrThrow

        val languages = remember {
            listOf(
                "English",
                "Ukrainian",
                "Spanish",
                "German",
                "Polish",
                "French",
                "Italian",
                "Chinese",
                "Turkish"
            )
        }

        var selected by remember { mutableStateOf(initialLanguage) }

        Scaffold(
            topBar = {
                AffirmationToolBar(
                    title = "Language",
                    showBack = true,
                    onBackClick = { navigator.pop() }
                )
            }
        ) { padding ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Brush.verticalGradient(listOf(pageBgTop, pageBgBottom)))
                    .padding(padding)
                    .padding(horizontal = 20.dp, vertical = 18.dp)
            ) {
                Text(
                    text = "Language",
                    color = Color(0xFF111827),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(top = 8.dp, bottom = 12.dp)
                )

                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    color = Color.White,
                    tonalElevation = 0.dp,
                    shadowElevation = 0.dp
                ) {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(vertical = 6.dp)
                    ) {
                        items(languages) { lang ->
                            LanguageRow(
                                title = lang,
                                isSelected = lang == selected,
                                onClick = {
                                    selected = lang
                                }
                            )

                            if (lang != languages.last()) {
                                HorizontalDivider(
                                    thickness = 1.dp,
                                    color = Color(0xFFE8EEF9),
                                    modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun LanguageRow(
    title: String,
    isSelected: Boolean,
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

        if (isSelected) {
            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = null,
                tint = Color(0xFF0434B4)
            )
        }
    }
}
