package com.mohit.mohitbloomapp.presentation.screens.dashboard.components.sleep

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Bedtime
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Remove
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mohit.mohitbloomapp.presentation.screens.dashboard.components.allinone.BloomInfoCard
import com.mohit.mohitbloomapp.presentation.screens.dashboard.components.allinone.BloomLinkText
import com.mohit.mohitbloomapp.presentation.screens.dashboard.components.allinone.BloomPillButton
import com.mohit.mohitbloomapp.presentation.screens.dashboard.components.allinone.BloomRoundButton
import com.mohit.mohitbloomapp.presentation.screens.dashboard.components.allinone.BloomStepper
import com.mohit.mohitbloomapp.presentation.screens.dashboard.components.allinone.BloomText
import com.mohit.mohitbloomapp.presentation.screens.dashboard.components.allinone.BloomTextStyle
import com.mohit.mohitbloomapp.presentation.screens.dashboard.components.allinone.BloomUi
import com.mohit.mohitbloomapp.ui.theme.Fraunces

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BloomLogSleepSheet(
    sleepHours: Int,
    goalHours: Int = 8,
    onSleepHoursChange: (Int) -> Unit,
    onDismiss: () -> Unit,
    onSettingsClick: () -> Unit = {}
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        containerColor = BloomUi.Background,
        contentColor = BloomUi.TextDark,
        tonalElevation = 0.dp,
        dragHandle = {
            Box(
                modifier = Modifier
                    .padding(top = 9.dp, bottom = 5.dp)
                    .size(width = 48.dp, height = 5.dp)
                    .clip(CircleShape)
                    .background(BloomUi.Border)
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(horizontal = 20.dp)
                .padding(top = 8.dp, bottom = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // ---------- Header ----------
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Log Sleep",
                    modifier = Modifier.weight(1f),
                    fontSize = 24.sp,
                    fontFamily = Fraunces,
                    fontWeight = W600,
                    fontStyle = FontStyle.Italic,
                    color = BloomUi.TextDark
                )
                BloomRoundButton(
                    icon = Icons.Outlined.Close,
                    contentDescription = "Close",
                    onClick = onDismiss,
                    size = 36.dp
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            BloomText(text = "Last night's sleep", style = BloomTextStyle.Label)

            Spacer(modifier = Modifier.height(12.dp))

            BloomStepper(
                value = "$sleepHours",
                unit = if (sleepHours == 1) "hour" else "hours",
                minusIcon = Icons.Outlined.Remove,
                plusIcon = Icons.Outlined.Add,
                onMinus = { onSleepHoursChange((sleepHours - 1).coerceAtLeast(0)) },
                onPlus = { onSleepHoursChange((sleepHours + 1).coerceAtMost(24)) }
            )

            Spacer(modifier = Modifier.height(14.dp))

            BloomLinkText(
                prefix = "Goal: $goalHours hours · adjust in ",
                linkText = "Settings",
                onLinkClick = onSettingsClick
            )

            Spacer(modifier = Modifier.height(16.dp))

            BloomInfoCard(
                title = sleepTipTitle(sleepHours, goalHours),
                body = sleepTipBody(sleepHours, goalHours),
                icon = Icons.Outlined.Bedtime,
                action = {
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        BloomPillButton(
                            text = "+1 hour",
                            onClick = { onSleepHoursChange((sleepHours + 1).coerceAtMost(24)) }
                        )
                        BloomPillButton(
                            text = "Log $goalHours hours",
                            onClick = { onSleepHoursChange(goalHours) }
                        )
                    }
                }
            )
        }
    }
}

private fun sleepTipTitle(hours: Int, goal: Int) = when {
    hours == 0 -> "Log your sleep"
    hours >= goal -> "Well rested!"
    else -> "A little more rest"
}

private fun sleepTipBody(hours: Int, goal: Int) = when {
    hours == 0 ->
        "You haven't logged sleep yet. Consistent, close-to-" +
                "8-hr nights can help keep your hormones more" +
                "balanced — avoiding phones and screens before bed" +
                "makes it easier to fall asleep."
    hours < goal ->
        "You're ${goal - hours} ${if (goal - hours == 1) "hour" else "hours"} short of your goal. Try an earlier wind-down tonight — dim lights and put screens away."
    else ->
        "You've hit your sleep goal. Great for hormone balance and energy — keep your bedtime routine steady."
}