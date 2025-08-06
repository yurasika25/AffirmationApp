package com.affirmation.app

import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin
import io.ktor.client.plugins.logging.Logger
import platform.Foundation.NSLog
import platform.UIKit.UIDevice

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()
actual fun platformHttpClient(): HttpClient = HttpClient(Darwin)

actual fun platformLogger(): Logger = object : Logger {
    override fun log(message: String) {
        NSLog("KtorLogger: $message")
    }
}

