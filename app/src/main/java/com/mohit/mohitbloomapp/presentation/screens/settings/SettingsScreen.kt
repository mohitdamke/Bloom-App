package com.mohit.mohitbloomapp.presentation.screens.settings

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.DirectionsRun
import androidx.compose.material.icons.outlined.Bedtime
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.Mood
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.material.icons.outlined.Spa
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mohit.mohitbloomapp.presentation.screens.settings.components.BloomEditProfileSheet
import com.mohit.mohitbloomapp.presentation.screens.settings.components.BloomLiveNotificationPreviewSheet
import com.mohit.mohitbloomapp.presentation.screens.settings.components.BloomReminderTimeSheet
import com.mohit.mohitbloomapp.ui.theme.BloomColors

@Composable
fun SettingsScreen() {
    BloomSettingsScreen()
}

@Composable
fun BloomSettingsScreen(
    userName: String = "Sofia",
    onEditProfile: () -> Unit = {},
    onSignOut: () -> Unit = {}
) {
    val trackingGoals = listOf(
        Triple("Meals", Icons.Outlined.Restaurant, BloomColors.Primary),
        Triple("Movement", Icons.AutoMirrored.Outlined.DirectionsRun, BloomColors.Green),
        Triple("Water", Icons.Outlined.WaterDrop, BloomColors.Blue),
        Triple("Relaxation", Icons.Outlined.Spa, BloomColors.Purple),
        Triple("Sleep", Icons.Outlined.Bedtime, BloomColors.Indigo),
        Triple("Cycle", Icons.Outlined.WaterDrop, BloomColors.PrimaryDark),
        Triple("Symptom check-in", Icons.Outlined.Mood, BloomColors.Gold)
    )

    val enabledGoals = remember {
        mutableStateMapOf<String, Boolean>().apply {
            trackingGoals.forEach { put(it.first, true) }
        }
    }

    var remindersEnabled by rememberSaveable { mutableStateOf(true) }
    var reminderTimeText by rememberSaveable { mutableStateOf("08:00 AM") }

    var showEditProfileSheet by rememberSaveable { mutableStateOf(false) }
    var showReminderSheet by rememberSaveable { mutableStateOf(false) }
    var showLiveNotificationSheet by rememberSaveable { mutableStateOf(false) }

    var currentUserName by rememberSaveable(userName) {
        mutableStateOf(userName)
    }
    var currentJourneyStage by rememberSaveable {
        mutableStateOf("General wellness")
    }

    var mealsTarget by rememberSaveable { mutableIntStateOf(3) }
    var movementTarget by rememberSaveable { mutableIntStateOf(30) }
    var waterTarget by rememberSaveable { mutableIntStateOf(8) }
    var relaxationTarget by rememberSaveable { mutableIntStateOf(15) }
    var sleepTarget by rememberSaveable { mutableIntStateOf(8) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BloomColors.Background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 18.dp)
                .padding(top = 26.dp, bottom = 24.dp)
        ) {
            // Header
            Text(
                text = "Settings",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = BloomColors.Text
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Manage goals, reminders & profile preferences",
                fontSize = 14.sp,
                color = BloomColors.Muted
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Profile Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(
                    containerColor = BloomColors.Card
                ),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(54.dp)
                            .clip(CircleShape)
                            .background(BloomColors.SoftPink.copy(alpha = 0.4f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.PersonOutline,
                            contentDescription = "Profile",
                            tint = BloomColors.Primary,
                            modifier = Modifier.size(28.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = currentUserName,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = BloomColors.Text
                        )

                        Spacer(modifier = Modifier.height(2.dp))

                        Text(
                            text = "Wellness Seeker",
                            fontSize = 12.sp,
                            color = BloomColors.Muted
                        )
                    }

                    OutlinedButton(
                        onClick = {
                            onEditProfile()
                            showEditProfileSheet = true
                        },
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = BloomColors.Primary
                        )
                    ) {
                        Text("Edit", fontSize = 12.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Active Tracking Goals
            SectionTitle("Active Tracking Goals")

            Spacer(modifier = Modifier.height(10.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(
                    containerColor = BloomColors.Card
                ),
                elevation = CardDefaults.cardElevation(1.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    trackingGoals.forEach { (name, icon, color) ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(CircleShape)
                                        .background(color.copy(alpha = 0.15f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = icon,
                                        contentDescription = name,
                                        tint = color,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.width(14.dp))

                                Text(
                                    text = name,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = BloomColors.Text
                                )
                            }

                            Switch(
                                checked = enabledGoals[name] ?: true,
                                onCheckedChange = { enabledGoals[name] = it },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = BloomColors.Card,
                                    checkedTrackColor = BloomColors.Primary,
                                    uncheckedThumbColor = BloomColors.Muted,
                                    uncheckedTrackColor = BloomColors.Sand
                                )
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Daily Goal Targets
            SectionTitle("Daily goal targets")

            Spacer(modifier = Modifier.height(10.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(
                    containerColor = BloomColors.Card
                ),
                elevation = CardDefaults.cardElevation(1.dp)
            ) {
                Column {
                    GoalTargetRow(
                        name = "Meals",
                        value = "$mealsTarget meals",
                        icon = Icons.Outlined.Restaurant,
                        iconColor = BloomColors.Primary,
                        onDecrease = {
                            if (mealsTarget > 1) mealsTarget--
                        },
                        onIncrease = { mealsTarget++ }
                    )

                    GoalTargetRow(
                        name = "Movement",
                        value = "$movementTarget min",
                        icon = Icons.AutoMirrored.Outlined.DirectionsRun,
                        iconColor = BloomColors.Green,
                        onDecrease = {
                            if (movementTarget > 5) movementTarget -= 5
                        },
                        onIncrease = { movementTarget += 5 }
                    )

                    GoalTargetRow(
                        name = "Water",
                        value = "$waterTarget glasses",
                        icon = Icons.Outlined.WaterDrop,
                        iconColor = BloomColors.Blue,
                        onDecrease = {
                            if (waterTarget > 1) waterTarget--
                        },
                        onIncrease = { waterTarget++ }
                    )

                    GoalTargetRow(
                        name = "Relaxation",
                        value = "$relaxationTarget min",
                        icon = Icons.Outlined.Spa,
                        iconColor = BloomColors.Purple,
                        onDecrease = {
                            if (relaxationTarget > 5) relaxationTarget -= 5
                        },
                        onIncrease = { relaxationTarget += 5 }
                    )

                    GoalTargetRow(
                        name = "Sleep",
                        value = "$sleepTarget hrs",
                        icon = Icons.Outlined.Bedtime,
                        iconColor = BloomColors.Indigo,
                        onDecrease = {
                            if (sleepTarget > 1) sleepTarget--
                        },
                        onIncrease = {
                            if (sleepTarget < 24) sleepTarget++
                        },
                        showDivider = false
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Notification Settings
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(
                    containerColor = BloomColors.Card
                ),
                elevation = CardDefaults.cardElevation(1.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = "Notifications",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = BloomColors.Text
                            )

                            Text(
                                text = "Daily reminders to log",
                                fontSize = 12.sp,
                                color = BloomColors.Muted
                            )
                        }

                        Switch(
                            checked = remindersEnabled,
                            onCheckedChange = { remindersEnabled = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = BloomColors.Card,
                                checkedTrackColor = BloomColors.Primary,
                                uncheckedThumbColor = BloomColors.Muted,
                                uncheckedTrackColor = BloomColors.Sand
                            )
                        )
                    }

                    HorizontalDivider(color = BloomColors.Sand)

                    // Reminder Time
                    SettingsActionRow(
                        title = "Reminder time",
                        subtitle = reminderTimeText,
                        onClick = { showReminderSheet = true }
                    )

                    HorizontalDivider(color = BloomColors.Sand)

                    // Live Notification Preview
                    SettingsActionRow(
                        title = "Live notification preview",
                        subtitle = "See lock-screen updates",
                        onClick = { showLiveNotificationSheet = true }
                    )

                    HorizontalDivider(color = BloomColors.Sand)

                    // App Version
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "App version",
                            fontSize = 14.sp,
                            color = BloomColors.Text
                        )

                        Text(
                            text = "1.0 · Prototype",
                            fontSize = 12.sp,
                            color = BloomColors.Muted
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Sign Out
            OutlinedButton(
                onClick = onSignOut,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(
                    1.dp,
                    BloomColors.Primary.copy(alpha = 0.35f)
                ),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = BloomColors.Primary
                )
            ) {
                Text(
                    text = "Sign out",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        // Edit Profile Bottom Sheet
        if (showEditProfileSheet) {
            BloomEditProfileSheet(
                userName = currentUserName,
                selectedJourneyStage = currentJourneyStage,
                onSave = { updatedName, updatedStage ->
                    currentUserName = updatedName
                    currentJourneyStage = updatedStage
                    showEditProfileSheet = false
                },
                onDismiss = {
                    showEditProfileSheet = false
                }
            )
        }

        // Reminder Time Bottom Sheet
        if (showReminderSheet) {
            BloomReminderTimeSheet(
                selectedTime = reminderTimeText,
                onSave = { selectedTime ->
                    reminderTimeText = selectedTime
                    showReminderSheet = false
                },
                onDismiss = {
                    showReminderSheet = false
                }
            )
        }

        // Live Notification Preview Bottom Sheet
        if (showLiveNotificationSheet) {
            BloomLiveNotificationPreviewSheet(
                onDismiss = {
                    showLiveNotificationSheet = false
                }
            )
        }
    }
}

@Composable
private fun SectionTitle(title: String) {
    Text(
        text = title,
        fontSize = 17.sp,
        fontWeight = FontWeight.SemiBold,
        fontStyle = FontStyle.Italic,
        color = BloomColors.Text
    )
}

@Composable
private fun SettingsActionRow(
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = BloomColors.Text
            )

            Text(
                text = subtitle,
                fontSize = 12.sp,
                color = BloomColors.Muted
            )
        }

        Icon(
            imageVector = Icons.Outlined.ChevronRight,
            contentDescription = null,
            tint = BloomColors.Muted
        )
    }
}

@Composable
private fun GoalTargetRow(
    name: String,
    value: String,
    icon: ImageVector,
    iconColor: Color,
    onDecrease: () -> Unit,
    onIncrease: () -> Unit,
    showDivider: Boolean = true
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(iconColor.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = name,
                    tint = iconColor,
                    modifier = Modifier.size(17.dp)
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = name,
                modifier = Modifier.weight(1f),
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = BloomColors.Text
            )

            OutlinedButton(
                onClick = onDecrease,
                modifier = Modifier.size(30.dp),
                contentPadding = PaddingValues(0.dp),
                shape = CircleShape,
                border = BorderStroke(1.dp, BloomColors.Sand)
            ) {
                Text(
                    text = "−",
                    fontSize = 16.sp,
                    color = BloomColors.Primary
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = value,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = BloomColors.Text
            )

            Spacer(modifier = Modifier.width(8.dp))

            OutlinedButton(
                onClick = onIncrease,
                modifier = Modifier.size(30.dp),
                contentPadding = PaddingValues(0.dp),
                shape = CircleShape,
                border = BorderStroke(1.dp, BloomColors.Sand)
            ) {
                Text(
                    text = "+",
                    fontSize = 16.sp,
                    color = BloomColors.Primary
                )
            }
        }

        if (showDivider) {
            HorizontalDivider(
                color = BloomColors.Sand.copy(alpha = 0.5f)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun BloomSettingsScreenPreview() {
    MaterialTheme {
        BloomSettingsScreen()
    }
}