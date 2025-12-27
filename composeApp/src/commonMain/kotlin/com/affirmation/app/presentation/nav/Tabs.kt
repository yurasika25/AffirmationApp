package com.affirmation.app.presentation.nav

import affirmationapp.composeapp.generated.resources.Res
import affirmationapp.composeapp.generated.resources.bell
import affirmationapp.composeapp.generated.resources.heart
import affirmationapp.composeapp.generated.resources.home
import affirmationapp.composeapp.generated.resources.user
import com.affirmation.app.presentation.screen.main.FavoriteScreen
import com.affirmation.app.presentation.screen.main.HomeScreen
import com.affirmation.app.presentation.screen.main.NotificationScreen
import com.affirmation.app.presentation.screen.main.ProfileScreen

object HomeTab : AppTab("home", "Home", Res.drawable.home, HomeScreen(), 0u)
object SavedTab : AppTab("saved", "Saved", Res.drawable.heart, FavoriteScreen(), 1u)
object RemindersTab : AppTab("reminders", "Reminders", Res.drawable.bell, NotificationScreen(), 2u)
object MeTab : AppTab("me", "Me", Res.drawable.user, ProfileScreen(), 3u)
