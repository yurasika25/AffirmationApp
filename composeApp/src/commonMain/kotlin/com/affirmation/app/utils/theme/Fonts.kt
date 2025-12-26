package com.affirmation.app.utils.theme

import affirmationapp.composeapp.generated.resources.Res
import affirmationapp.composeapp.generated.resources.dancing_script_regular
import affirmationapp.composeapp.generated.resources.dancing_script_semi_bold
import affirmationapp.composeapp.generated.resources.inter_semi_bold
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import org.jetbrains.compose.resources.Font

@Composable
fun dancingSemiBoldFont(): FontFamily {
    return FontFamily(
        Font(Res.font.dancing_script_semi_bold)
    )
}

@Composable
fun dancingRegularFont(): FontFamily {
    return FontFamily(
        Font(Res.font.dancing_script_regular)
    )
}

@Composable
fun interSemiBoldFont(): FontFamily {
    return FontFamily(
        Font(Res.font.inter_semi_bold)
    )
}