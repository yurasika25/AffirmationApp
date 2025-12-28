package com.affirmation.app

import android.app.Application
import com.affirmation.app.main.di.doInitKoin
import org.koin.android.ext.koin.androidContext

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        doInitKoin {
            androidContext(this@App)
        }
    }
}