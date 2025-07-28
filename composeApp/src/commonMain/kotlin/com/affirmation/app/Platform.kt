package com.affirmation.app

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform