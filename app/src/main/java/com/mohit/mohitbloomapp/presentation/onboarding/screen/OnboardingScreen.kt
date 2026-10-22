package com.mohit.mohitbloomapp.presentation.onboarding.screen

import android.app.TimePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mohit.mohitbloomapp.presentation.onboarding.viewmodel.OnboardingViewModel
import com.mohit.mohitbloomapp.ui.theme.BloomBackground
import com.mohit.mohitbloomapp.ui.theme.BloomBrown
import com.mohit.mohitbloomapp.ui.theme.BloomPink
import com.mohit.mohitbloomapp.ui.theme.BloomText
import java.util.Calendar

@Composable
fun OnboardingScreen(
    onComplete: () -> Unit,
    viewModel: OnboardingViewModel = viewModel()
) {
    var step by remember { mutableIntStateOf(0) }

    val name by viewModel.userName.collectAsState()
    val goals by viewModel.selectedGoals.collectAsState()
    val reminderTime by viewModel.reminderTime.collectAsState()
    val reminderEnabled by viewModel.reminderEnabled.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BloomBackground)
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp)
    ) {
        Spacer(Modifier.height(24.dp))

        if (step > 0) {
            TextButton(onClick = { step-- }) {
                Text("‹ Back", color = BloomBrown)
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            repeat(4) { index ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(3.dp)
                        .background(
                            if (index <= step) BloomBrown
                            else BloomPink,
                            RoundedCornerShape(4.dp)
                        )
                )
            }
        }

        when (step) {

            0 -> {
                Spacer(Modifier.weight(1f))

                Text(
                    text = "✿",
                    fontSize = 76.sp,
                    color = BloomBrown,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Text(
                    text = "Welcome to Bloom",
                    fontSize = 27.sp,
                    fontStyle = FontStyle.Italic,
                    color = BloomText,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(Modifier.height(12.dp))

                Text(
                    text = "A simple daily companion for managing " +
                            "PCOS — track your cycle, meals, movement, " +
                            "water, relaxation and sleep.",
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    fontSize = 13.sp
                )

                Spacer(Modifier.weight(1f))

                BloomButton("Get started") {
                    step = 1
                }

                Spacer(Modifier.height(20.dp))
            }

            1 -> {
                Spacer(Modifier.height(24.dp))

                Text(
                    "A little about you",
                    fontSize = 23.sp,
                    fontStyle = FontStyle.Italic,
                    color = BloomText,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(Modifier.height(24.dp))

                OutlinedTextField(
                    value = name,
                    onValueChange = viewModel::updateName,
                    placeholder = { Text("Your first name") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = BloomBrown,
                        unfocusedBorderColor = BloomPink
                    )
                )

                Spacer(Modifier.weight(1f))

                BloomButton(
                    text = "Continue",
                    enabled = name.isNotBlank()
                ) {
                    step = 2
                }

                Spacer(Modifier.height(20.dp))
            }

            2 -> {
                Spacer(Modifier.height(20.dp))

                Text(
                    "✳",
                    fontSize = 34.sp,
                    color = BloomBrown,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Text(
                    "What would you like to track daily?",
                    fontSize = 20.sp,
                    fontStyle = FontStyle.Italic,
                    textAlign = TextAlign.Center,
                    color = BloomText
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    "Pick the goals that matter to you.",
                    color = Color.Gray,
                    fontSize = 12.sp
                )

                Spacer(Modifier.height(12.dp))

                val descriptions = listOf(
                    "Meals" to "Balanced meals",
                    "Movement" to "Gentle movement",
                    "Water" to "Stay hydrated",
                    "Relaxation" to "Relaxation and mindfulness",
                    "Sleep" to "Consistent sleep",
                    "Cycle" to "Cycle and period tracking"
                )

                descriptions.forEach { (goal, description) ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(Modifier.weight(1f)) {
                            Text(
                                goal,
                                color = BloomText,
                                fontSize = 14.sp
                            )
                            Text(
                                description,
                                color = Color.Gray,
                                fontSize = 11.sp
                            )
                        }

                        Switch(
                            checked = goal in goals,
                            onCheckedChange = {
                                viewModel.toggleGoal(goal, it)
                            },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = BloomBrown
                            )
                        )
                    }

                    HorizontalDivider(color = BloomPink.copy(alpha = 0.4f))
                }

                Spacer(Modifier.weight(1f))

                BloomButton("Continue") {
                    step = 3
                }

                Spacer(Modifier.height(20.dp))
            }

            3 -> {
                Spacer(Modifier.height(28.dp))

                Text(
                    "◷",
                    fontSize = 40.sp,
                    color = BloomBrown,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Text(
                    "When should we remind you?",
                    fontSize = 21.sp,
                    fontStyle = FontStyle.Italic,
                    textAlign = TextAlign.Center,
                    color = BloomText
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    "We'll send one daily nudge to log how you're doing.",
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    color = Color.Gray
                )

                Spacer(Modifier.height(24.dp))

                val context = LocalContext.current

                listOf(
                    "Morning" to "07:30 AM",
                    "Afternoon" to "01:00 PM",
                    "Evening" to "08:00 PM"
                ).forEach { (label, time) ->
                    OutlinedButton(
                        onClick = {
                            viewModel.updateReminderTime(time)
                            viewModel.updateReminderEnabled(true)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = BloomText
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {
                            Text(label)
                            Text(
                                time,
                                color = Color.Gray,
                                fontSize = 11.sp
                            )
                        }
                    }
                }

                Spacer(Modifier.height(12.dp))

                OutlinedButton(
                    onClick = {
                        val calendar = Calendar.getInstance()

                        TimePickerDialog(
                            context,
                            { _, hour, minute ->
                                val formattedHour =
                                    if (hour % 12 == 0) 12 else hour % 12

                                val period =
                                    if (hour < 12) "AM" else "PM"

                                viewModel.updateReminderTime(
                                    "%02d:%02d %s".format(
                                        formattedHour,
                                        minute,
                                        period
                                    )
                                )
                                viewModel.updateReminderEnabled(true)
                            },
                            calendar.get(Calendar.HOUR_OF_DAY),
                            calendar.get(Calendar.MINUTE),
                            false
                        ).show()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Custom time: $reminderTime")
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = reminderEnabled,
                        onCheckedChange = viewModel::updateReminderEnabled
                    )
                    Text("Enable daily reminder")
                }

                Spacer(Modifier.weight(1f))

                BloomButton("Enter Bloom") {
                    viewModel.completeOnboarding {
                        onComplete()
                    }
                }

                TextButton(
                    onClick = {
                        viewModel.updateReminderEnabled(false)
                        viewModel.completeOnboarding {
                            onComplete()
                        }
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(
                        "I'll set this up later",
                        color = BloomText
                    )
                }

                Spacer(Modifier.height(12.dp))
            }
        }
    }
}

@Composable
private fun BloomButton(
    text: String,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = BloomBrown,
            disabledContainerColor = BloomPink
        )
    ) {
        Text(text, color = Color.White)
    }
}

@Preview
@Composable
private fun OnboardingScreenPreview() {
    OnboardingScreen(
        onComplete = {}
    )
}