package com.affirmation.app.presentation.nav.ext

import cafe.adriel.voyager.navigator.Navigator

fun Navigator.root(): Navigator {
    var current: Navigator = this
    while (current.parent != null) {
        current = current.parent!!
    }
    return current
}