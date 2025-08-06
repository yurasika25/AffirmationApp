package com.affirmation.app

import android.os.Build
import android.util.Log
import io.ktor.client.plugins.logging.Logger

class AndroidPlatform: Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

actual fun platformLogger(): Logger = object : Logger {
    override fun log(message: String) {
        Log.d("KtorLogger", message)
    }
}