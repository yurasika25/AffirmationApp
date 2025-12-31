package com.affirmation.app.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.affirmation.app.presentation.nav.HomeTab
import com.affirmation.app.presentation.nav.MeTab
import com.affirmation.app.presentation.nav.RemindersTab
import com.affirmation.app.presentation.nav.SavedTab

class TabsHostScreen : Screen {

    @Composable
    override fun Content() {
        val tabs = remember { listOf(HomeTab, SavedTab, RemindersTab, MeTab) }

        TabNavigator(HomeTab) { tabNavigator ->
            Scaffold(
                contentWindowInsets = WindowInsets(0),
                bottomBar = {
                    val bottomBarShape = RoundedCornerShape(topStart = 22.dp, topEnd = 22.dp)
                    NavigationBar(
                        containerColor = Color(0xFFFAFBFF),
                        modifier = Modifier.clip(bottomBarShape)
                    ) {
                        tabs.forEachIndexed { _, tab ->
                            val selected = tabNavigator.current.key == tab.key
                            NavigationBarItem(
                                selected = selected,
                                onClick = { tabNavigator.current = tab },
                                icon = {
                                    tab.options.icon?.let {
                                        Icon(
                                            it,
                                            contentDescription = null,
                                            modifier = Modifier.size(22.dp)
                                        )
                                    }
                                },
                                label = { Text(tab.options.title) },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = Color(0xFF0434B4),
                                    selectedTextColor = Color(0xFF0434B4),
                                    indicatorColor = Color(0xFFE7F0FF),
                                    unselectedIconColor = Color(0xFF616268),
                                    unselectedTextColor = Color(0xFF616268)
                                )
                            )
                        }
                    }
                }

            ) { innerPadding ->
                val layoutDirection = LocalLayoutDirection.current

                val contentPadding = PaddingValues(
                    start = innerPadding.calculateStartPadding(layoutDirection),
                    top = innerPadding.calculateTopPadding(),
                    end = innerPadding.calculateEndPadding(layoutDirection)
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(contentPadding)
                ) {
                    CurrentTab()
                }
            }
        }
    }
}