package com.mohit.mohitbloomapp.presentation.onboarding.screen

import android.app.TimePickerDialog
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mohit.mohitbloomapp.R
import com.mohit.mohitbloomapp.ui.theme.BloomBackground
import com.mohit.mohitbloomapp.ui.theme.BloomBrown
import com.mohit.mohitbloomapp.ui.theme.BloomColors
import com.mohit.mohitbloomapp.ui.theme.BloomColors.welcomeText
import com.mohit.mohitbloomapp.ui.theme.BloomText
import com.mohit.mohitbloomapp.ui.theme.Fraunces
import com.mohit.mohitbloomapp.ui.theme.PlusJakartaSans
import java.util.Calendar
import java.util.Locale

@Composable
fun ReminderScreen(
    reminderTime: String,
    reminderEnabled: Boolean,
    onReminderTimeChange: (String) -> Unit,
    onReminderEnabledChange: (Boolean) -> Unit,
    onComplete: () -> Unit
) {
    val context = LocalContext.current

    val reminderOptions = listOf(
        Triple("Morning", "07:30 AM", "Start the day on track"),
        Triple("Afternoon", "01:00 PM", "Take a moment for yourself"),
        Triple("Evening", "08:00 PM", "Reflect on your day")
    )

    var selectedPreset by remember {
        mutableStateOf("07:30 AM")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BloomBackground)
            .padding(10.dp)
    ) {
        // Logo, heading and description — unchanged
        Image(
            painter = painterResource(R.drawable.clock),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(40.dp)
        )

        Spacer(Modifier.height(12.dp))

        Text(
            text = "When should we remind you?",
            modifier = Modifier.fillMaxWidth(),
            fontSize = 24.sp,
            fontFamily = Fraunces,
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.SemiBold,
            color = BloomText,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(10.dp))

        Text(
            text = "We'll send one daily nudge to log how you're doing.",
            modifier = Modifier.fillMaxWidth(),
            fontFamily = PlusJakartaSans,
            fontSize = 14.sp,
            color = welcomeText,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(28.dp))

        Text(
            text = "Pick a time of day",
            fontFamily = PlusJakartaSans,
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            color = BloomText
        )

        Spacer(Modifier.height(12.dp))

        // Three reminder options
        reminderOptions.forEach { (label, time, description) ->
            val isSelected = selectedPreset == time

            val selectReminder = {
                selectedPreset = time
                onReminderTimeChange(time)
                onReminderEnabledChange(true)
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        if (isSelected) {
                            BloomColors.SoftPink.copy(alpha = 0.25f)
                        } else {
                            BloomColors.Card
                        }
                    )
                    .border(
                        width = 1.dp,
                        color = if (isSelected) BloomBrown else BloomColors.Sand,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .clickable { selectReminder() }
                    .padding(horizontal = 12.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = isSelected,
                    onClick = { selectReminder() },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = BloomBrown,
                        unselectedColor = BloomColors.Muted
                    )
                )

                Spacer(Modifier.width(8.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = label,
                        fontFamily = PlusJakartaSans,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = BloomText
                    )

                    Text(
                        text = "$time - $description",
                        fontFamily = PlusJakartaSans,
                        fontSize = 12.sp,
                        color = welcomeText
                    )
                }
            }
        }


        Spacer(Modifier.height(8.dp))

        Text(
            text = "Or set a custom time",
            fontFamily = PlusJakartaSans,
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            color = BloomText
        )

        Spacer(Modifier.height(12.dp))

        // Custom time field
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(BloomColors.Card)
                .border(
                    width = 1.dp,
                    color = BloomColors.Sand,
                    shape = RoundedCornerShape(14.dp)
                )
                .clickable {
                    val calendar = Calendar.getInstance()

                    val parts = reminderTime
                        .replace(" AM", "")
                        .replace(" PM", "")
                        .split(":")

                    val hour = parts.getOrNull(0)
                        ?.toIntOrNull() ?: 8

                    val minute = parts.getOrNull(1)
                        ?.toIntOrNull() ?: 0

                    val hour24 = when {
                        reminderTime.endsWith("PM", ignoreCase = true) &&
                                hour != 12 -> hour + 12

                        reminderTime.endsWith("AM", ignoreCase = true) &&
                                hour == 12 -> 0

                        else -> hour
                    }

                    TimePickerDialog(
                        context,
                        { _, selectedHour, selectedMinute ->
                            val hour12 = if (selectedHour % 12 == 0) {
                                12
                            } else {
                                selectedHour % 12
                            }

                            val period = if (selectedHour < 12) "AM" else "PM"

                            val formattedTime = String.format(
                                Locale.US,
                                "%02d:%02d %s",
                                hour12,
                                selectedMinute,
                                period
                            )

                            selectedPreset = ""
                            onReminderTimeChange(formattedTime)
                            onReminderEnabledChange(true)
                        },
                        hour24,
                        minute,
                        false
                    ).show()
                }
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = reminderTime,
                fontFamily = PlusJakartaSans,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = BloomText
            )

            Icon(
                imageVector = Icons.Outlined.AccessTime,
                contentDescription = "Choose time",
                tint = BloomBrown,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(Modifier.weight(1f))

        // Existing bottom button
        Button(
            onClick = onComplete,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = BloomBrown
            )
        ) {
            Text(
                text = "Enter Bloom",
                color = Color.White,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = PlusJakartaSans,
                fontSize = 18.sp
            )
        }

        Text(text = "I'll set this up later",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
                .clickable { onComplete() },
            fontFamily = PlusJakartaSans,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = welcomeText,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ReminderScreenPreview() {
    MaterialTheme {
        ReminderScreen(
            reminderTime = "08:00 AM",
            reminderEnabled = true,
            onReminderTimeChange = {},
            onReminderEnabledChange = {},
            onComplete = {}
        )
    }
}
