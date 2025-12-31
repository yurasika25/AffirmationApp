package com.affirmation.app.main.di

import com.affirmation.app.createHttpClient
import com.affirmation.app.data.network.ApiService
import com.affirmation.app.main.playercontroller.IosPlayerController
import com.affirmation.app.presentation.screen.player.controller.PlayerController
import org.koin.dsl.module

actual val platformModule = module {
    factory<PlayerController> { IosPlayerController() }
    single { createHttpClient() }
    single { ApiService(client = get()) }
}