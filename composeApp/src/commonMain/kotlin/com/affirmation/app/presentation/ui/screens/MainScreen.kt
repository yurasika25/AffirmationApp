package com.affirmation.app.presentation.ui.screens

import affirmationapp.composeapp.generated.resources.Res
import affirmationapp.composeapp.generated.resources.bell
import affirmationapp.composeapp.generated.resources.heart
import affirmationapp.composeapp.generated.resources.home
import affirmationapp.composeapp.generated.resources.user
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import org.jetbrains.compose.resources.painterResource

class MainScreen : Screen {
    @Composable
    override fun Content() {
        val items = listOf(
            NavItem(
                label = "Home",
                icon = {
                    Icon(
                        painterResource(Res.drawable.home),
                        modifier = Modifier.size(24.dp),
                        contentDescription = "Home"
                    )
                },
                screen = { HomeScreen() }
            ),
            NavItem(
                label = "Favorites",
                icon = {
                    Icon(
                        painterResource(Res.drawable.heart),
                        modifier = Modifier.size(24.dp),
                        contentDescription = "Favorites"
                    )
                },
                screen = { FavoriteScreen() }
            ),
            NavItem(
                label = "Notifications",
                icon = {
                    Icon(
                        painterResource(Res.drawable.bell),
                        modifier = Modifier.size(24.dp),
                        contentDescription = "Notifications"
                    )
                },
                screen = { NotificationScreen() }
            ),
            NavItem(
                label = "Profile",
                icon = {
                    Icon(
                        painterResource(Res.drawable.user),
                        modifier = Modifier.size(24.dp),
                        contentDescription = "Profile"
                    )
                },
                screen = { ProfileScreen() }
            )
        )

        var selectedIndex by remember { mutableStateOf(0) }

        Scaffold(
            bottomBar = {
                NavigationBar {
                    items.forEachIndexed { index, item ->
                        NavigationBarItem(
                            icon = { item.icon() },
                            label = { Text(item.label) },
                            selected = selectedIndex == index,
                            onClick = { selectedIndex = index }
                        )
                    }
                }
            }
        ) { innerPadding ->
            Box(Modifier.padding(innerPadding)) {
                items.forEachIndexed { index, item ->
                    if (index == selectedIndex) {
                        Navigator(item.screen()) {
                            CurrentScreen()
                        }
                    }
                }
            }
        }
    }
}

data class NavItem(
    val label: String,
    val icon: @Composable () -> Unit,
    val screen: () -> Screen
)






