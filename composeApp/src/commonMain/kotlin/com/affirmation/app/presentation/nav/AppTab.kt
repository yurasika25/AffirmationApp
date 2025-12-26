package com.affirmation.app.presentation.nav

import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import cafe.adriel.voyager.transitions.SlideTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

abstract class AppTab(
    tabKey: String,
    private val title: String,
    private val icon: DrawableResource,
    private val startScreen: Screen,
    private val index: UInt
) : Tab {

    override val key: String = tabKey

    @Composable
    override fun Content() {
        Navigator(startScreen) { nav ->
            SlideTransition(navigator = nav)
        }
    }

    override val options: TabOptions
        @Composable
        get() {
            val painter = painterResource(icon)
            return remember { TabOptions(index = index.toUShort(), title = title, icon = painter) }
        }
}
