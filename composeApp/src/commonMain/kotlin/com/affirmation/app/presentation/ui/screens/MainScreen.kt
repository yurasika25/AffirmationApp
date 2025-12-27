package com.affirmation.app.presentation.ui.screens

import affirmationapp.composeapp.generated.resources.Res
import affirmationapp.composeapp.generated.resources.bell
import affirmationapp.composeapp.generated.resources.heart
import affirmationapp.composeapp.generated.resources.home
import affirmationapp.composeapp.generated.resources.user
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.affirmation.app.domain.model.NavItem
import com.affirmation.app.presentation.nav.HomeTab
import com.affirmation.app.presentation.nav.MeTab
import com.affirmation.app.presentation.nav.RemindersTab
import com.affirmation.app.presentation.nav.SavedTab
import com.affirmation.app.utils.LocalBottomBarHideCount
import org.jetbrains.compose.resources.painterResource


class MainScreen : Screen {

    @Composable
    override fun Content() {

        val items = remember {
            listOf(
                NavItem("Home", Res.drawable.home),
                NavItem("Saved", Res.drawable.heart),
                NavItem("Reminders", Res.drawable.bell),
                NavItem("Me", Res.drawable.user)
            )
        }

        val hideCount = rememberSaveable { mutableIntStateOf(0) }
        val bottomBarVisible = hideCount.intValue == 0

        val tabs = remember { listOf(HomeTab, SavedTab, RemindersTab, MeTab) }


        TabNavigator(HomeTab) { tabNavigator ->

            CompositionLocalProvider(LocalBottomBarHideCount provides hideCount) {

                Scaffold(
                    contentWindowInsets = WindowInsets(0),
                    bottomBar = {
                        AnimatedVisibility(
                            visible = bottomBarVisible,
                            enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
                            exit = slideOutVertically(targetOffsetY = { it }) + fadeOut()
                        ) {

                            val bottomBarShape = RoundedCornerShape(
                                topStart = 22.dp,
                                topEnd = 22.dp
                            )

                            NavigationBar(
                                containerColor = Color(0xFFFAFBFF),
                                tonalElevation = 0.dp,
                                modifier = Modifier
                                    .clip(bottomBarShape)

                            ) {
                                tabs.forEachIndexed { index, tab ->
                                    val selected = tabNavigator.current.key == tab.key
                                    NavigationBarItem(
                                        selected = selected,
                                        onClick = { tabNavigator.current = tab },
                                        icon = {
                                            Icon(
                                                painter = painterResource(items[index].icon),
                                                contentDescription = items[index].label,
                                                modifier = Modifier.size(22.dp)
                                            )
                                        },
                                        label = { Text(items[index].label) },
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
                    }
                ) { innerPadding ->
                    val layoutDirection = LocalLayoutDirection.current

                    val contentPadding = PaddingValues(
                        start = innerPadding.calculateStartPadding(layoutDirection),
                        top = innerPadding.calculateTopPadding(),
                        end = innerPadding.calculateEndPadding(layoutDirection),
                        bottom = 0.dp
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
}