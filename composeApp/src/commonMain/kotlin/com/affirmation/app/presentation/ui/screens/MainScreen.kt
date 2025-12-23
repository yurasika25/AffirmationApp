package com.affirmation.app.presentation.ui.screens

import affirmationapp.composeapp.generated.resources.Res
import affirmationapp.composeapp.generated.resources.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import cafe.adriel.voyager.core.screen.Screen
import com.affirmation.app.domain.model.NavItem
import org.jetbrains.compose.resources.painterResource

class MainScreen : Screen {
    @Composable
    override fun Content() {
        val screens = listOf(InspirationHomeScreen(), FavoriteScreen(), NotificationScreen(), ProfileScreen())
        val items = listOf(
            NavItem("Home", Res.drawable.home),
            NavItem("Favorites", Res.drawable.heart),
            NavItem("Notifications", Res.drawable.bell),
            NavItem("Profile", Res.drawable.user)
        )

        var selectedIndex by remember { mutableStateOf(0) }

        Scaffold(
            bottomBar = {
                    NavigationBar {
                    items.forEachIndexed { index, item ->
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    painter = painterResource(item.icon),
                                    contentDescription = item.label,
                                    modifier = Modifier.size(24.dp)
                                )
                            },
                            label = { Text(item.label) },
                            selected = selectedIndex == index,
                            onClick = { selectedIndex = index }
                        )
                    }
                }
            }
        ) { innerPadding ->
            Box(Modifier.padding(bottom = 50.dp, start = 0.dp, end = 0.dp)) {
                screens[selectedIndex].Content()
            }
        }
    }
}





