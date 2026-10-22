package com.mohit.mohitbloomapp.presentation.screens.settings.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.SelfImprovement
import androidx.compose.material.icons.outlined.TrackChanges
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import com.mohit.mohitbloomapp.ui.theme.BloomColors
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private enum class PreviewType(
    val title: String,
    val icon: ImageVector
) {
    MEDITATION("Meditation", Icons.Outlined.SelfImprovement),
    DAILY_GOAL("Daily goal", Icons.Outlined.TrackChanges),
    WATER("Water", Icons.Outlined.WaterDrop)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BloomLiveNotificationPreviewSheet(
    onDismiss: () -> Unit
) {
    var selectedType by remember {
        mutableStateOf(PreviewType.MEDITATION)
    }

    var isRunning by remember {
        mutableStateOf(false)
    }

    var remainingSeconds by remember {
        mutableIntStateOf(300)
    }

    var elapsedWater by remember {
        mutableIntStateOf(0)
    }

    var completedGoals by remember {
        mutableIntStateOf(0)
    }

    LaunchedEffect(isRunning, selectedType) {
        while (isRunning) {
            delay(1000)

            when (selectedType) {
                PreviewType.MEDITATION -> {
                    if (remainingSeconds > 0) {
                        remainingSeconds--
                    } else {
                        isRunning = false
                    }
                }

                PreviewType.DAILY_GOAL -> {
                    // Demo progress is controlled by the Begin button.
                }

                PreviewType.WATER -> {
                    // Demo water progress is controlled by the Begin button.
                }
            }
        }
    }

    val currentTime = remember {
        mutableStateOf("")
    }

    val currentDate = remember {
        mutableStateOf("")
    }

    LaunchedEffect(Unit) {
        while (true) {
            val now = Date()

            currentTime.value = SimpleDateFormat(
                "h:mm a",
                Locale.US
            ).format(now)

            currentDate.value = SimpleDateFormat(
                "EEE, MMM d",
                Locale.US
            ).format(now)

            delay(1000)
        }
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = BloomColors.Background,
        shape = RoundedCornerShape(
            topStart = 28.dp,
            topEnd = 28.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(bottom = 28.dp)
        ) {
            Text(
                text = "Live Notification Preview",
                fontSize = 23.sp,
                fontWeight = FontWeight.Bold,
                color = BloomColors.Text
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "A preview of how Bloom could show live, real-time updates on the lock screen",
                fontSize = 14.sp,
                lineHeight = 21.sp,
                color = BloomColors.Muted
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Preview type selector
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                PreviewType.entries.forEach { type ->
                    val selected = selectedType == type

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(14.dp))
                            .background(
                                if (selected) {
                                    BloomColors.Primary.copy(alpha = 0.12f)
                                } else {
                                    BloomColors.Card
                                }
                            )
                            .clickable {
                                selectedType = type
                                isRunning = false
                            }
                            .padding(vertical = 12.dp, horizontal = 4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = type.icon,
                            contentDescription = type.title,
                            tint = if (selected) {
                                BloomColors.Primary
                            } else {
                                BloomColors.Muted
                            },
                            modifier = Modifier.size(22.dp)
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = type.title,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = if (selected) {
                                BloomColors.Primary
                            } else {
                                BloomColors.Text
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Simulated lock screen
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(26.dp))
                    .background(Color(0xFF171717))
                    .padding(18.dp)
            ) {
                Text(
                    text = currentTime.value,
                    fontSize = 42.sp,
                    fontWeight = FontWeight.Light,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = currentDate.value,
                    fontSize = 15.sp,
                    color = Color.White.copy(alpha = 0.8f)
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Notification card
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(18.dp))
                        .background(Color(0xFF303030))
                        .padding(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(BloomColors.Primary),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "B",
                                fontSize = 19.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = "Bloom",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White
                            )

                            Text(
                                text = "LIVE ACTIVITY",
                                fontSize = 10.sp,
                                color = Color.White.copy(alpha = 0.65f)
                            )
                        }

                        Box(
                            modifier = Modifier
                                .size(7.dp)
                                .clip(CircleShape)
                                .background(
                                    if (isRunning) {
                                        Color(0xFF71D69B)
                                    } else {
                                        Color.Gray
                                    }
                                )
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    when (selectedType) {
                        PreviewType.MEDITATION -> {
                            Text(
                                text = if (isRunning) {
                                    "Meditation: ${formatTime(remainingSeconds)} remaining"
                                } else {
                                    "Meditation: ${formatTime(remainingSeconds)} remaining"
                                },
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            LinearProgressIndicator(
                                progress = {
                                    1f - remainingSeconds / 300f
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(5.dp)
                                    .clip(CircleShape),
                                color = BloomColors.Primary,
                                trackColor = Color.White.copy(alpha = 0.2f)
                            )
                        }

                        PreviewType.DAILY_GOAL -> {
                            Text(
                                text = "Daily goals: $completedGoals of 5 completed",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            LinearProgressIndicator(
                                progress = {
                                    completedGoals / 5f
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(5.dp)
                                    .clip(CircleShape),
                                color = BloomColors.Primary,
                                trackColor = Color.White.copy(alpha = 0.2f)
                            )
                        }

                        PreviewType.WATER -> {
                            Text(
                                text = "Water: $elapsedWater of 8 glasses",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            LinearProgressIndicator(
                                progress = {
                                    elapsedWater / 8f
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(5.dp)
                                    .clip(CircleShape),
                                color = BloomColors.Primary,
                                trackColor = Color.White.copy(alpha = 0.2f)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Begin and Reset buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = {
                        when (selectedType) {
                            PreviewType.MEDITATION -> {
                                if (remainingSeconds <= 0) {
                                    remainingSeconds = 300
                                }
                            }

                            PreviewType.DAILY_GOAL -> {
                                if (completedGoals < 5) {
                                    completedGoals++
                                }
                            }

                            PreviewType.WATER -> {
                                if (elapsedWater < 8) {
                                    elapsedWater++
                                }
                            }
                        }

                        isRunning = selectedType == PreviewType.MEDITATION &&
                                remainingSeconds > 0
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = BloomColors.Primary
                    )
                ) {
                    Text(
                        text = if (isRunning) "Running" else "Begin",
                        fontWeight = FontWeight.SemiBold
                    )
                }

                OutlinedButton(
                    onClick = {
                        isRunning = false
                        remainingSeconds = 300
                        completedGoals = 0
                        elapsedWater = 0
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    shape = RoundedCornerShape(14.dp),
                    border = BorderStroke(
                        1.dp,
                        BloomColors.Primary.copy(alpha = 0.5f)
                    )
                ) {
                    Text(
                        text = "Reset",
                        color = BloomColors.Primary,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Updates live while a meditation session is running — same pattern as an order-tracking or workout notification.",
                fontSize = 12.sp,
                lineHeight = 18.sp,
                color = BloomColors.Muted
            )
        }
    }
}

private fun formatTime(seconds: Int): String {
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60

    return String.format(
        Locale.US,
        "%d:%02d",
        minutes,
        remainingSeconds
    )
}