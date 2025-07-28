package com.affirmation.app.ui

import affirmationapp.composeapp.generated.resources.Res
import affirmationapp.composeapp.generated.resources.im_me
import affirmationapp.composeapp.generated.resources.image1
import affirmationapp.composeapp.generated.resources.image10
import affirmationapp.composeapp.generated.resources.image2
import affirmationapp.composeapp.generated.resources.image3
import affirmationapp.composeapp.generated.resources.image4
import affirmationapp.composeapp.generated.resources.image5
import affirmationapp.composeapp.generated.resources.image6
import affirmationapp.composeapp.generated.resources.image7
import affirmationapp.composeapp.generated.resources.image8
import affirmationapp.composeapp.generated.resources.image9
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.affirmation.app.model.MotivationItem
import com.affirmation.app.utils.monthNames
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.painterResource

class HomeScreen : Screen {
    @Composable
    override fun Content() {

        val todayFormatted = remember {
            val currentDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

            val monthName = monthNames[currentDateTime.monthNumber - 1]
            "$monthName ${currentDateTime.dayOfMonth}, ${currentDateTime.year}"
        }

        var showDialog by remember { mutableStateOf(false) }
        val navigator = LocalNavigator.currentOrThrow
        val items = listOf(
            MotivationItem(
                Res.drawable.image1,
                "Believe in yourself",
                "When Sarah first decided to start her own business, everyone told her it was impossible. She had no money, no experience, and no one to guide her. But she believed in herself when no one else did. Every day she faced rejection and failure, yet she kept going, reminding herself that her dreams were worth the fight. Years later, that little idea she held onto turned into a thriving company that inspires others to believe in their own potential. Your journey may be different, but the lesson remains: trust yourself, even when it feels like the world doesn’t."
            ),
            MotivationItem(
                Res.drawable.image2,
                "Stay positive and happy",
                "Daniel was stuck in a job he hated and saw no light in his days. One morning, he challenged himself to list three good things daily—simple moments like a warm cup of coffee or a stranger’s smile. At first, it felt forced, but slowly his perspective shifted. He started noticing beauty in small things and learned that happiness doesn’t come from perfect circumstances but from training your mind to see the good even when it’s hard."
            ),
            MotivationItem(
                Res.drawable.image3,
                "Work hard and never give up",
                "Maria wanted to run a marathon, but her first training session left her breathless and sore. She thought of quitting countless times, but she kept reminding herself why she started. Day by day, she pushed through the pain, ran a little farther, and grew a little stronger. When she crossed the finish line months later, she realized the marathon wasn’t just about running—it was about proving to herself that persistence beats every obstacle."
            ),
            MotivationItem(
                Res.drawable.image4,
                "Be kind whenever possible",
                "James paid for the coffee of the person behind him, not knowing that it would spark a chain of kindness. That person helped a colleague, who then supported a struggling family. Small acts, though often unseen, ripple far beyond what we imagine. Being kind isn’t just about others—it transforms you, too. And sometimes, your smallest gesture becomes someone else’s reason to keep going."
            ),
            MotivationItem(
                Res.drawable.image5,
                "Dream big and dare to fail",
                "Liam dreamed of becoming a filmmaker, but with no connections, no equipment, and no experience, everyone told him it was foolish. He started with a cheap camera and made hundreds of unseen, imperfect videos. Every failure became a stepping stone. Years later, those same people who doubted him celebrated his films. Big dreams aren’t about avoiding failure—they’re about daring to fail until success becomes inevitable."
            ),
            MotivationItem(
                Res.drawable.image6,
                "Your limitation—it’s only your imagination",
                "Emma believed she wasn’t creative enough to paint. One day, she decided to try anyway. Her first paintings were clumsy and far from perfect, but each canvas revealed something new about herself. Over time, she created works that even she couldn’t believe came from her hands. Our limits are often self-imposed. Once you break them, you’ll discover how boundless your potential really is."
            ),
            MotivationItem(
                Res.drawable.image7,
                "Push yourself",
                "When Kevin trained for his first triathlon, he realized the biggest battle wasn’t physical—it was in his head. Every time he wanted to stop, he asked himself, ‘What if I can do just one more step?’ That simple question changed everything. It taught him that growth lives just beyond discomfort. The next time you want to quit, push yourself one step further—you’ll be surprised by what you’re capable of."
            ),
            MotivationItem(
                Res.drawable.image8,
                "Great things never come from comfort zones",
                "Clara lived a safe, predictable life until she decided to take a job abroad, leaving everything familiar behind. The first months were scary and lonely, but slowly she grew, learned a new language, made new friends, and discovered strengths she never knew she had. Comfort zones feel safe, but nothing grows there. Step out, and you’ll find a life worth living."
            ),
            MotivationItem(
                Res.drawable.image9,
                "Success doesn’t just find you",
                "Anthony used to wait for opportunities, thinking success would knock on his door. It never did. One day, he decided to knock on doors himself—networking, learning, and putting himself out there. The change didn’t happen overnight, but his persistence paid off. Success rarely finds you—you have to chase it with everything you’ve got."
            ),
            MotivationItem(
                Res.drawable.image10,
                "Make today count",
                "After a health scare, Olivia realized she’d been waiting for ‘the right time’ to live. She stopped postponing joy and started making each day matter—calling her parents more, pursuing her passions, and savoring simple pleasures. Life isn’t promised. The best time to start living fully is now. Make today count, because tomorrow is never guaranteed."
            ),
            MotivationItem(
                Res.drawable.image1,
                "Failure is part of success",
                "Michael once feared failure so much that he avoided trying new things. But after losing his first startup, he discovered that failure isn’t the opposite of success—it’s part of the journey. Each failure taught him lessons that later built his thriving second business. Embrace failure as your teacher, not your enemy."
            ),
            MotivationItem(
                Res.drawable.image2,
                "Gratitude changes everything",
                "Sophia kept a gratitude journal when life felt heavy. Writing three things she appreciated daily—no matter how small—transformed her outlook. Soon, she felt lighter, happier, and more resilient. Gratitude doesn’t change your circumstances—it changes you."
            ),
            MotivationItem(
                Res.drawable.image3,
                "Be relentless",
                "Jordan faced rejection letters from dozens of universities. Instead of giving up, he wrote to professors directly, improved his portfolio, and kept applying. Eventually, one acceptance letter changed his entire future. Relentlessness isn’t about luck—it’s about refusing to stop when doors close."
            ),
            MotivationItem(
                Res.drawable.image4,
                "Learn every day",
                "After losing her job, Mia committed to learning one new skill daily. What started as watching free tutorials turned into a new career path she loved. Growth isn’t built on big leaps—it’s made of small, consistent steps."
            ),
            MotivationItem(
                Res.drawable.image5,
                "Surround yourself with positivity",
                "When Ethan switched from hanging around negative influences to joining a group of motivated, like-minded people, his mindset—and life—completely changed. Your environment shapes you. Choose people who lift you up, not pull you down."
            ),
            MotivationItem(
                Res.drawable.image6,
                "Be patient with yourself",
                "Lily thought progress meant constant improvement. When she hit a plateau, she felt like a failure. But with time, she learned that growth is slow, nonlinear, and often invisible. Be patient. Seeds take time to grow, and so do you."
            ),
            MotivationItem(
                Res.drawable.image7,
                "Choose courage over comfort",                "Every time Noah avoided tough conversations, his relationships grew distant. One day, he chose courage over comfort—speaking openly, even when it was hard. It changed everything. Courage isn’t the absence of fear; it’s acting despite it."
            ),
            MotivationItem(
                Res.drawable.image8,
                "Small steps lead to big change",
                "Sophie wanted to lose 50 pounds, but the goal felt impossible. Instead of focusing on the number, she started by walking 10 minutes a day, then 20, then 30. Months later, she reached her goal. Big changes are just small steps repeated consistently."
            ),
            MotivationItem(
                Res.drawable.image9,
                "Your story isn’t over",
                "After a painful breakup, Liam thought his life was over. But time, healing, and self-discovery helped him rebuild a life he loved. Whatever chapter you’re in, remember—your story isn’t over. Better pages are yet to come."
            ),
            MotivationItem(
                Res.drawable.image10,
                "Be your own hero",
                "When Zoe stopped waiting for others to save her and took responsibility for her own happiness, everything changed. She found strength she didn’t know she had. You are the hero of your story—start acting like it."
            )
        )


        Scaffold(
            topBar = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .statusBarsPadding()
                ) {
                    Column {
                        Text(
                            text = "Hi, Yurii!",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black
                        )
                        Text(
                            modifier = Modifier.padding(top = 8.dp),
                            text = "Today is $todayFormatted",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Card(
                        elevation = CardDefaults.cardElevation(0.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.Gray),
                        shape = CircleShape,
                        modifier = Modifier
                            .width(63.dp)
                            .height(63.dp)
                            .clickable {
                                showDialog  = true
                            }
                    ) {

                        Image(
                            painter = painterResource(Res.drawable.im_me),
                            contentDescription = "Avatar",
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }

        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(items) { item ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(34F)
                    ) {
                        Box(
                            modifier = Modifier
                                .height(120.dp)
                                .fillMaxSize()
                                .clickable {
                                    navigator.push(DetailScreen(
                                        icon = item.icon,
                                        title = item.text,
                                        subtitle = item.subtitle
                                    ))
                                }
                        ) {
                            Image(
                                painter = painterResource(item.icon),
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                            Text(
                                text = item.text,
                                color = Color.White,
                                fontSize = 20.sp,
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .padding(8.dp)
                            )
                        }
                    }
                }
            }
        }
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("My Profile") },
                text = { Text("Are you sure you want to edit your profile?") },
                confirmButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}