package com.affirmation.app.main.di

import com.affirmation.app.main.playercontroller.IosPlayerController
import org.koin.dsl.module
import com.affirmation.app.presentation.screen.player.controller.PlayerController

actual val platformModule = module {
    factory<PlayerController> { IosPlayerController() }
}