import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import affirmationapp.composeapp.generated.resources.Res
import affirmationapp.composeapp.generated.resources.im_back_btn
import affirmationapp.composeapp.generated.resources.im_setting
import androidx.compose.foundation.layout.size
import com.affirmation.app.presentation.theme.interSemiBoldFont
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AffirmationToolBar(
    title: String = "My Profile",
    showBack: Boolean = false,
    onBackClick: (() -> Unit)? = null,
    showSettings: Boolean = true,
    onSettingsClick: (() -> Unit)? = null,
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                fontFamily = interSemiBoldFont(),
                fontWeight = FontWeight.Normal,
                fontSize = 24.sp
            )
        },
        navigationIcon = {
            if (showBack && onBackClick != null) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        painter = painterResource(Res.drawable.im_back_btn),
                        contentDescription = "Back",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        },
        actions = {
            if (showSettings && onSettingsClick != null) {
                IconButton(onClick = onSettingsClick) {
                    Icon(
                        painter = painterResource(Res.drawable.im_setting),
                        contentDescription = "Settings",
                        modifier = Modifier.size(24.dp),
                        tint = Color(0xFF5B7DBE)
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFFF7FAFF),
            titleContentColor = Color(0xFF111827),
            actionIconContentColor = Color(0xFF111827),
            navigationIconContentColor = Color(0xFF111827)
        )
    )
}
