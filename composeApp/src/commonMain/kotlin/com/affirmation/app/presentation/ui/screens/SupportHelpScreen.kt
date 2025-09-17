package com.affirmation.app.presentation.ui.screens

import affirmationapp.composeapp.generated.resources.Res
import affirmationapp.composeapp.generated.resources.arrow_back
import affirmationapp.composeapp.generated.resources.done_circle_red_heart
import affirmationapp.composeapp.generated.resources.paper_clip_attach
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource

class SupportHelpScreen : Screen {

    @Composable
    override fun Content() {
        val nav = LocalNavigator.currentOrThrow

        val pageBg = Color(0xFFFAF7FF)
        val lilac = Color(0xFFB99BF7)
        val lilacSoft = Color(0xFFF0E9FF)

        val focus = LocalFocusManager.current
        val keyboard = LocalSoftwareKeyboardController.current

        var message by rememberSaveable { mutableStateOf("") }
        var submitted by rememberSaveable { mutableStateOf(false) }

        LaunchedEffect(submitted) {
            if (submitted) {
                focus.clearFocus(force = true)
                keyboard?.hide()
            }
        }

        val maxChars = 1000
        val count = message.length.coerceAtMost(maxChars)
        val progress = (count / maxChars.toFloat()).coerceIn(0f, 1f)
        val canSend = message.trim().length >= 5
        val autoReturnMs = 4000L

        if (!submitted) {
            Scaffold(containerColor = pageBg) { inner ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(inner)
                        .padding(horizontal = 16.dp)
                        .pointerInput(Unit) {
                            detectTapGestures(onTap = {
                                focus.clearFocus(force = true)
                                keyboard?.hide()
                            })
                        }
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.arrow_back),
                        contentDescription = "Arrow back icon",
                        tint = Color(0xFF9985D0),
                        modifier = Modifier
                            .size(32.dp)
                            .clickable {
                                focus.clearFocus(force = true)
                                keyboard?.hide()
                                nav.pop()
                            }
                    )

                    Spacer(Modifier.height(6.dp))
                    Text("Support and Help", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "How can we help you?",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "We are ready to help. Describe your question, and our team will contact you.",
                        color = Color(0xFF7D7796),
                        fontSize = 14.sp
                    )

                    Spacer(Modifier.height(14.dp))
                    Text(
                        "Please provide as much detail as possible",
                        color = Color(0xFFB0A9C5),
                        fontSize = 13.sp
                    )

                    Spacer(Modifier.height(8.dp))

                    OutlinedTextField(
                        value = message,
                        onValueChange = { message = it.take(maxChars) },
                        placeholder = { Text("Describe your issue here…") },
                        minLines = 10,
                        maxLines = 14,
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = lilac,
                            unfocusedBorderColor = lilacSoft,
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            cursorColor = lilac
                        ),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                focus.clearFocus(force = true)
                                keyboard?.hide()
                            }
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        val counter = buildAnnotatedString {
                            withStyle(SpanStyle(color = lilac, fontWeight = FontWeight.Medium)) {
                                append(count.toString())
                            }
                            append("/")
                            withStyle(SpanStyle(color = Color(0xFFB0A9C5))) {
                                append(maxChars.toString())
                            }
                        }
                        Text(counter)
                    }

                    LinearProgressIndicator(
                        progress = { progress },
                        trackColor = lilacSoft,
                        color = lilac,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(RoundedCornerShape(3.dp))
                            .padding(top = 6.dp)
                    )

                    Spacer(Modifier.height(18.dp))

                    Text("Attachments (Optional)", fontWeight = FontWeight.SemiBold)
                    Spacer(Modifier.height(8.dp))
                    AttachmentDropZone(
                        hint = "Attach files (images, documents)",
                        borderColor = lilac,
                        background = Color(0x11B99BF7),
                        onClick = { /* TODO: open file picker */ }
                    )

                    Spacer(Modifier.height(24.dp))

                    Button(
                        onClick = {
                            focus.clearFocus(force = true)
                            keyboard?.hide()
                            // TODO: send ticket to backend
                            submitted = true
                        },
                        enabled = canSend,
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = lilac,
                            contentColor = Color.White,
                            disabledContainerColor = lilac.copy(alpha = .4f),
                            disabledContentColor = Color.White
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp)
                    ) {
                        Text("Send Request", fontSize = 16.sp, fontWeight = FontWeight.Medium)
                    }

                    Spacer(Modifier.height(24.dp))
                }
            }
        } else {
            ThankYouForSupport(
                accent = lilac,
                autoReturnMs = autoReturnMs,
                onAutoBack = { nav.pop() }
            )
        }
    }
}


@Composable
private fun ThankYouForSupport(
    accent: Color,
    autoReturnMs: Long,
    onAutoBack: () -> Unit
) {
    var bar by remember { mutableStateOf(0f) }

    LaunchedEffect(autoReturnMs) {
        val steps = 60
        val stepMs = (autoReturnMs / steps).coerceAtLeast(16)
        repeat(steps) { i ->
            delay(stepMs)
            bar = (i + 1) / steps.toFloat()
        }
        onAutoBack()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(contentAlignment = Alignment.TopEnd) {
            Box(
                modifier = Modifier
                    .size(98.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(Res.drawable.done_circle_red_heart),
                    contentDescription = "Done circle icon",
                    modifier = Modifier
                        .height(98.dp)
                        .width(90.dp),
                    contentScale = ContentScale.Fit
                )
            }

        }

        Spacer(Modifier.height(16.dp))
        Text("Thank you!", fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
        Spacer(Modifier.height(8.dp))
        Text(
            "Your message has been sent. We will review it and contact you shortly.",
            color = Color(0xFF6E6A7B),
            fontSize = 14.sp,
            lineHeight = 20.sp,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(14.dp))

        Surface(
            color = Color(0xFFF0E9FF),
            shape = RoundedCornerShape(18.dp)
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 18.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("💡")
                Spacer(Modifier.width(8.dp))
                Text("We typically respond within 24 hours")
            }
        }

        Spacer(Modifier.height(28.dp))

        Button(
            onClick = onAutoBack,
            shape = RoundedCornerShape(18.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = accent,
                contentColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) { Text("Continue to App", fontSize = 16.sp, fontWeight = FontWeight.Medium) }

        Spacer(Modifier.height(16.dp))
        Text(
            "Automatically returning to the app in a few seconds…",
            color = Color(0xFF9E9EAB),
            fontSize = 12.sp
        )
        Spacer(Modifier.height(8.dp))
        LinearProgressIndicator(
            progress = { bar },
            color = accent,
            trackColor = Color(0xFFE0E0E0),
            modifier = Modifier
                .width(160.dp)
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp))
        )
    }
}

@Composable
private fun AttachmentDropZone(
    hint: String,
    borderColor: Color,
    background: Color,
    onClick: () -> Unit
) {
    val radius = 16.dp
    val dash = with(androidx.compose.ui.platform.LocalDensity.current) { 8.dp.toPx() }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(76.dp)
            .clip(RoundedCornerShape(radius))
            .background(background)
            .drawBehind {
                drawRoundRect(
                    color = borderColor,
                    size = size,
                    cornerRadius = androidx.compose.ui.geometry.CornerRadius(
                        radius.toPx(),
                        radius.toPx()
                    ),
                    style = Stroke(
                        width = 2.dp.toPx(),
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(dash, dash), 0f)
                    )
                )
            }
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(Res.drawable.paper_clip_attach),
                contentDescription = "Arrow back icon",
                tint = Color(0xFF5B5B5B),
                modifier = Modifier
                    .size(16.dp)
            )
            Spacer(Modifier.width(8.dp))
            Text(hint, color = Color(0xFF7D7796))
        }
    }
}
