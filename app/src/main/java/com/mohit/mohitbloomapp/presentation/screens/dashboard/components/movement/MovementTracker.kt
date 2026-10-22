package com.mohit.mohitbloomapp.presentation.screens.dashboard.components.movement

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.DirectionsRun
import androidx.compose.material.icons.outlined.Remove
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mohit.mohitbloomapp.presentation.screens.dashboard.components.allinone.BloomInfoCard
import com.mohit.mohitbloomapp.presentation.screens.dashboard.components.allinone.BloomLinkText
import com.mohit.mohitbloomapp.presentation.screens.dashboard.components.allinone.BloomPillButton
import com.mohit.mohitbloomapp.presentation.screens.dashboard.components.allinone.BloomStepper
import com.mohit.mohitbloomapp.presentation.screens.dashboard.components.allinone.BloomText
import com.mohit.mohitbloomapp.presentation.screens.dashboard.components.allinone.BloomTextStyle
import com.mohit.mohitbloomapp.presentation.screens.dashboard.components.allinone.BloomUi

/**
 * "Today's movement" section, built only from reusable Bloom components.
 *
 * Usage:
 *   var minutes by remember { mutableStateOf(0) }
 *   MovementTracker(
 *       minutes = minutes,
 *       goalMinutes = 30,
 *       onDecrement = { minutes = (minutes - 5).coerceAtLeast(0) },
 *       onIncrement = { minutes += 5 },
 *       onQuickAdd = { minutes += 10 },
 *       onSettingsClick = { /* open settings */ }
 *   )
 */
@Composable
fun MovementTracker(
    minutes: Int,
    goalMinutes: Int,
    onDecrement: () -> Unit,
    onIncrement: () -> Unit,
    onQuickAdd: () -> Unit,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier,
    quickAddMinutes: Int = 10,
    tipTitle: String = defaultTipTitle(minutes, goalMinutes),
    tipBody: String = defaultTipBody(minutes, goalMinutes)
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(BloomUi.Background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BloomText(text = "Today's movement", style = BloomTextStyle.Label)

        Spacer(modifier = Modifier.height(12.dp))

        BloomStepper(
            value = "$minutes",
            unit = "min",
            minusIcon = Icons.Outlined.Remove,
            plusIcon = Icons.Outlined.Add,
            onMinus = onDecrement,
            onPlus = onIncrement
        )

        Spacer(modifier = Modifier.height(14.dp))

        BloomLinkText(
            prefix = "Goal: $goalMinutes min · adjust in ",
            linkText = "Settings",
            onLinkClick = onSettingsClick
        )

        Spacer(modifier = Modifier.height(16.dp))

        BloomInfoCard(
            title = tipTitle,
            body = tipBody,
            icon = Icons.Outlined.DirectionsRun,
            action = {
                BloomPillButton(text = "+$quickAddMinutes min", onClick = onQuickAdd)
            }
        )
    }
}

private fun defaultTipTitle(minutes: Int, goal: Int) = when {
    minutes == 0 -> "Get moving today"
    minutes >= goal -> "Goal reached!"
    else -> "Keep it going"
}

private fun defaultTipBody(minutes: Int, goal: Int) = when {
    minutes == 0 ->
        "No movement logged yet today. Try a brisk 30-minute walk, a swim, or some light strength training — these are especially good for insulin sensitivity with PCOS."
    minutes < goal ->
        "Nice start! ${goal - minutes} more minutes gets you to today's goal. A short walk after your next meal can help too."
    else ->
        "You've hit your movement goal today. Great for insulin sensitivity — keep listening to your body and rest when you need to."
}