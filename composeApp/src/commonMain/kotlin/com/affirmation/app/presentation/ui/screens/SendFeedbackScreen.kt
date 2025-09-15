package com.affirmation.app.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.ImeAction
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.delay

class SendFeedbackScreen(
    private val onSubmit: (rating: Int, message: String) -> Unit = { _, _ -> },
    private val autoReturnMs: Long = 3000L
) : Screen {

    @Composable
    override fun Content() {
        val nav = LocalNavigator.currentOrThrow

        val bg = Color(0xFFFAF7FF)
        val lilac = Color(0xFF9B87E0)
        val lilacSoft = Color(0xFFEDE6FF)

        val focus = LocalFocusManager.current
        val keyboard = LocalSoftwareKeyboardController.current

        var rating by rememberSaveable { mutableStateOf(0) }
        var message by rememberSaveable { mutableStateOf("") }
        var submitted by rememberSaveable { mutableStateOf(false) }

        LaunchedEffect(submitted) {
            if (submitted) {
                focus.clearFocus(force = true)
                keyboard?.hide()
            }
        }

        if (!submitted) {
            val maxChars = 1000
            val count = message.length.coerceAtMost(maxChars)
            val progress = (count / maxChars.toFloat()).coerceIn(0f, 1f)
            val canSend = rating > 0 && message.trim().length >= 5

            Scaffold(containerColor = bg) { inner ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(inner)
                        .padding(horizontal = 20.dp)
                        .pointerInput(Unit) {
                            detectTapGestures(onTap = {
                                focus.clearFocus(force = true)
                                keyboard?.hide()
                            })
                        }
                ) {
                    Text(
                        text = "←",
                        color = lilac,
                        fontSize = 20.sp,
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .clip(CircleShape)
                            .clickable {
                                focus.clearFocus(force = true)
                                keyboard?.hide()
                                nav.pop()
                            }
                            .padding(8.dp)
                    )

                    Spacer(Modifier.height(6.dp))

                    Text(
                        "Send Feedback",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(14.dp))
                    Text(
                        "We’d love your feedback",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(
                        "Share your impressions or suggestions so we can make the application better.",
                        color = Color(0xFF7D7796),
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(18.dp))
                    Text(
                        "How would you rate your experience?",
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Spacer(Modifier.height(10.dp))

                    RatingRow(
                        value = rating,
                        onChange = { rating = it },
                        activeColor = lilac,
                        inactiveColor = Color(0xFFCBC6DE)
                    )

                    Spacer(Modifier.height(16.dp))

                    OutlinedTextField(
                        value = message,
                        onValueChange = { message = it.take(maxChars) },
                        placeholder = { Text("Write your review here…") },
                        minLines = 6,
                        maxLines = 12,
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
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                        val counter = buildAnnotatedString {
                            withStyle(SpanStyle(color = Color(0xFF9090A5))) { append("${count}") }
                            append("/")
                            withStyle(SpanStyle(color = Color(0xFFB0A9C5))) { append(maxChars.toString()) }
                        }
                        Text(counter)
                    }

                    Spacer(Modifier.height(6.dp))
                    LinearProgressIndicator(
                        progress = { progress },
                        color = Color(0xFFBDBDBD),
                        trackColor = Color(0xFFE0E0E0),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(RoundedCornerShape(3.dp))
                    )

                    Spacer(Modifier.height(28.dp))
                    Button(
                        onClick = {
                            focus.clearFocus(force = true)
                            keyboard?.hide()
                            onSubmit(rating, message.trim())
                            submitted = true
                        },
                        enabled = canSend,
                        shape = RoundedCornerShape(18.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = lilac,
                            contentColor = Color.White,
                            disabledContainerColor = lilac.copy(alpha = 0.4f),
                            disabledContentColor = Color.White
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                    ) {
                        Text("Send Request", fontSize = 16.sp, fontWeight = FontWeight.Medium)
                    }

                    Spacer(Modifier.height(24.dp))
                }
            }
        } else {
            ThankYouContent(
                rating = rating,
                accent = lilac,
                autoReturnMs = autoReturnMs,
                onAutoBack = { nav.pop() }
            )
        }
    }
}


@Composable
private fun RatingRow(
    value: Int,
    onChange: (Int) -> Unit,
    activeColor: Color,
    inactiveColor: Color
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(18.dp, alignment = Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        (1..5).forEach { i ->
            val selected = i <= value
            Text(
                text = if (selected) "★" else "☆",
                color = if (selected) activeColor else inactiveColor,
                fontSize = 28.sp,
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable { onChange(i) }
                    .padding(4.dp)
            )
        }
    }
}

@Composable
private fun ThankYouContent(
    rating: Int,
    accent: Color,
    autoReturnMs: Long,
    onAutoBack: () -> Unit
) {
    var progress by remember { mutableStateOf(0f) }

    LaunchedEffect(autoReturnMs) {
        val steps = 60
        val stepMs = (autoReturnMs / steps).coerceAtLeast(16)
        repeat(steps) { i ->
            delay(stepMs)
            progress = (i + 1) / steps.toFloat()
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
                    .size(96.dp)
                    .clip(CircleShape)
                    .background(Color(0x2228A745)),
                contentAlignment = Alignment.Center
            ) { Text("✅", fontSize = 44.sp) }
            Text("❤", modifier = Modifier.offset(x = 8.dp, y = (-8).dp), fontSize = 20.sp)
        }

        Spacer(Modifier.height(18.dp))
        Text("Thank You!", fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
        Spacer(Modifier.height(6.dp))
        Text(
            "We're thrilled you're enjoying the app!",
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(12.dp))
        Text(
            "Your positive feedback motivates our team to keep creating meaningful experiences for your daily affirmation practice.",
            color = Color(0xFF7D7796),
            fontSize = 14.sp,
            lineHeight = 20.sp,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(18.dp))

        Surface(
            color = Color(0xFFF0E9FF),
            shape = RoundedCornerShape(18.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(horizontal = 18.dp, vertical = 12.dp)
            ) {
                Text("Your Rating:", color = Color(0xFF7D7796))
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    (1..5).forEach { i ->
                        Text(
                            if (i <= rating) "★" else "☆",
                            color = if (i <= rating) accent else Color(0xFFCBC6DE),
                            fontSize = 20.sp
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(28.dp))

        Button(
            onClick = onAutoBack,
            shape = RoundedCornerShape(18.dp),
            colors = ButtonDefaults.buttonColors(containerColor = accent, contentColor = Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) { Text("Continue to App", fontSize = 16.sp, fontWeight = FontWeight.Medium) }

        Spacer(Modifier.height(12.dp))

        Surface(
            color = Color.White,
            shape = RoundedCornerShape(18.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0x22000000)),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clickable { /* TODO: open store rating */ }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text("♡", color = Color(0xFF1F1A33), fontSize = 18.sp)
                Spacer(Modifier.width(10.dp))
                Text("Rate us on the App Store", fontSize = 16.sp, fontWeight = FontWeight.Medium)
            }
        }

        Spacer(Modifier.height(16.dp))
        Text(
            "Automatically returning to the app in a few seconds…",
            color = Color(0xFF9E9EAB),
            fontSize = 12.sp
        )

        Spacer(Modifier.height(8.dp))
        LinearProgressIndicator(
            progress = { progress },
            color = accent,
            trackColor = Color(0xFFE0E0E0),
            modifier = Modifier
                .width(160.dp)
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp))
        )
    }
}
