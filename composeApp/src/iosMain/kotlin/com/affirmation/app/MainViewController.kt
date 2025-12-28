package com.affirmation.app
import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import com.affirmation.app.main.di.doInitKoin


fun MainViewController() = ComposeUIViewController {
    remember {
        doInitKoin()
        true
    }
    AffirmationApp()
}