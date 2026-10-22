package com.mohit.mohitbloomapp.presentation.screens.settings.components

import android.app.TimePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mohit.mohitbloomapp.ui.theme.BloomColors
import java.util.Calendar
import java.util.Locale

private data class ReminderOption(
    val title: String,
    val description: String,
    val time: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BloomReminderTimeSheet(
    selectedTime: String = "08:00 AM",
    onSave: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current

    val reminderOptions = listOf(
        ReminderOption(
            title = "Morning",
            description = "Start your day with a gentle reminder",
            time = "08:00 AM"
        ),
        ReminderOption(
            title = "Afternoon",
            description = "Check in with yourself during the day",
            time = "01:00 PM"
        ),
        ReminderOption(
            title = "Evening",
            description = "Reflect on your day before winding down",
            time = "08:00 PM"
        )
    )

    var selectedReminderTime by remember(selectedTime) {
        mutableStateOf(selectedTime)
    }

    var customTime by remember(selectedTime) {
        mutableStateOf(selectedTime)
    }

    var isCustomTime by remember(selectedTime) {
        mutableStateOf(selectedTime !in reminderOptions.map { it.time })
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = BloomColors.Background,
        shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(
                    androidx.compose.foundation.rememberScrollState()
                )
                .padding(horizontal = 20.dp)
                .padding(bottom = 24.dp)
        ) {
            Text(
                text = "Reminder Time",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = BloomColors.Text
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "When should Bloom nudge you to log your day?",
                fontSize = 14.sp,
                color = BloomColors.Muted,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Preset reminder options
            reminderOptions.forEach { option ->
                ReminderTimeOption(
                    option = option,
                    selected = !isCustomTime &&
                            selectedReminderTime == option.time,
                    onClick = {
                        selectedReminderTime = option.time
                        isCustomTime = false
                    }
                )

                Spacer(modifier = Modifier.height(10.dp))
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Or set a custom time",
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = BloomColors.Text
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Custom time picker
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(BloomColors.Card)
                    .clickable {
                        val calendar = Calendar.getInstance()

                        val parts = customTime
                            .replace(" AM", "")
                            .replace(" PM", "")
                            .split(":")

                        val hour = parts.getOrNull(0)
                            ?.toIntOrNull() ?: calendar.get(Calendar.HOUR)

                        val minute = parts.getOrNull(1)
                            ?.toIntOrNull() ?: 0

                        val hour24 = when {
                            customTime.endsWith("PM", ignoreCase = true) &&
                                    hour != 12 -> hour + 12

                            customTime.endsWith("AM", ignoreCase = true) &&
                                    hour == 12 -> 0

                            else -> hour
                        }

                        TimePickerDialog(
                            context,
                            { _, selectedHour, selectedMinute ->
                                val amPm =
                                    if (selectedHour >= 12) "PM" else "AM"

                                val formattedHour =
                                    if (selectedHour % 12 == 0) {
                                        12
                                    } else {
                                        selectedHour % 12
                                    }

                                customTime = String.format(
                                    Locale.US,
                                    "%02d:%02d %s",
                                    formattedHour,
                                    selectedMinute,
                                    amPm
                                )

                                selectedReminderTime = customTime
                                isCustomTime = true
                            },
                            hour24,
                            minute,
                            false
                        ).show()
                    }
                    .padding(horizontal = 16.dp, vertical = 18.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = customTime,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = BloomColors.Text
                )

                Icon(
                    imageVector = Icons.Outlined.AccessTime,
                    contentDescription = "Choose custom reminder time",
                    tint = BloomColors.Primary
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    onSave(selectedReminderTime)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = BloomColors.Primary
                )
            ) {
                Text(
                    text = "Save",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = BloomColors.Card
                )
            }
        }
    }
}

@Composable
private fun ReminderTimeOption(
    option: ReminderOption,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(BloomColors.Card)
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(
                selectedColor = BloomColors.Primary,
                unselectedColor = BloomColors.Muted
            )
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = option.title,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = BloomColors.Text
            )

            Spacer(modifier = Modifier.height(3.dp))

            Text(
                text = option.description,
                fontSize = 12.sp,
                color = BloomColors.Muted
            )
        }

        Text(
            text = option.time,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = BloomColors.Text
        )
    }
}