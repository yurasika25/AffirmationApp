package com.affirmation.app.main.di

import com.affirmation.app.main.playercontroller.AndroidPlayerController
import com.affirmation.app.presentation.screen.player.controller.PlayerController
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual val platformModule = module {
    factory<PlayerController> {
        AndroidPlayerController(context = androidContext())
    }
}