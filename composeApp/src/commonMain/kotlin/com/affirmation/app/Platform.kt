package com.affirmation.app

import io.ktor.client.plugins.logging.Logger

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
expect fun platformLogger(): Logger