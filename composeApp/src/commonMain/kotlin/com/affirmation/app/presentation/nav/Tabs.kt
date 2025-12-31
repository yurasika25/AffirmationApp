package com.affirmation.app.presentation.nav

import affirmationapp.composeapp.generated.resources.Res
import affirmationapp.composeapp.generated.resources.bell
import affirmationapp.composeapp.generated.resources.home
import affirmationapp.composeapp.generated.resources.im_saved
import affirmationapp.composeapp.generated.resources.user
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.jetbrains.compose.resources.painterResource
import com.affirmation.app.presentation.screen.main.SavedScreen
import com.affirmation.app.presentation.screen.main.InspirationHomeContent
import com.affirmation.app.presentation.screen.main.MyAccountScreen
import com.affirmation.app.presentation.screen.main.NotificationScreen

object HomeTab : Tab {
    override val key: String = "home"

    override val options: TabOptions
        @Composable get() = TabOptions(
            index = 0u,
            title = "Home",
            icon = painterResource(Res.drawable.home)
        )

    @Composable
    override fun Content() {
        InspirationHomeContent()
    }
}

object SavedTab : Tab {
    override val key: String = "saved"

    override val options: TabOptions
        @Composable get() = TabOptions(
            index = 1u,
            title = "Saved",
            icon = painterResource(Res.drawable.im_saved)
        )

    @Composable
    override fun Content() {
        SavedScreen()
    }
}

object RemindersTab : Tab {
    override val key: String = "reminders"

    override val options: TabOptions
        @Composable get() = TabOptions(
            index = 2u,
            title = "Reminders",
            icon = painterResource(Res.drawable.bell)
        )

    @Composable
    override fun Content() {
        NotificationScreen()
    }
}

object MeTab : Tab {
    override val key: String = "me"

    override val options: TabOptions
        @Composable get() = TabOptions(
            index = 3u,
            title = "Me",
            icon = painterResource(Res.drawable.user)
        )

    @Composable
    override fun Content() {
        MyAccountScreen()
    }
}
