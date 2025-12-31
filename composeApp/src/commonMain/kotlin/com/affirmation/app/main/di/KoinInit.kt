package com.affirmation.app.main.di

import com.affirmation.app.presentation.store.NotificationsStore
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun doInitKoin(
    appDeclaration: KoinAppDeclaration = {}
): KoinApplication {

    val commonModule = module {
        factory { NotificationsStore(api = get()) }
    }

    return startKoin {
        appDeclaration()
        modules(commonModule, platformModule)
    }
}
