package com.mohit.mohitbloomapp.presentation.screens.dashboard.components.relax

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Remove
import androidx.compose.material.icons.outlined.Spa
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
fun BloomLogRelaxationSheet(
    relaxationMinutes: Int,
    goalMinutes: Int = 15,
    onMinutesChange: (Int) -> Unit,
    onDismiss: () -> Unit,
    onSettingsClick: () -> Unit = {},
    stepMinutes: Int = 5
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
                    text = "Log Relaxation",
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

            BloomText(text = "Today's relaxation", style = BloomTextStyle.Label)

            Spacer(modifier = Modifier.height(12.dp))

            BloomStepper(
                value = "$relaxationMinutes",
                unit = "min",
                minusIcon = Icons.Outlined.Remove,
                plusIcon = Icons.Outlined.Add,
                onMinus = { onMinutesChange((relaxationMinutes - stepMinutes).coerceAtLeast(0)) },
                onPlus = { onMinutesChange(relaxationMinutes + stepMinutes) }
            )

            Spacer(modifier = Modifier.height(14.dp))

            BloomLinkText(
                prefix = "Goal: $goalMinutes min · adjust in ",
                linkText = "Settings",
                onLinkClick = onSettingsClick
            )

            Spacer(modifier = Modifier.height(16.dp))

            BloomInfoCard(
                title = relaxTipTitle(relaxationMinutes, goalMinutes),
                body = relaxTipBody(relaxationMinutes, goalMinutes),
                icon = Icons.Outlined.Spa,
                action = {
                    BloomPillButton(
                        text = "+$stepMinutes min",
                        onClick = { onMinutesChange(relaxationMinutes + stepMinutes) }
                    )
                }
            )
        }
    }
}

private fun relaxTipTitle(minutes: Int, goal: Int) = when {
    minutes == 0 -> "Take a mindful moment"
    minutes >= goal -> "Goal reached!"
    else -> "Keep unwinding"
}

private fun relaxTipBody(minutes: Int, goal: Int) = when {
    minutes == 0 ->
        "No relaxation time logged yet. Even 5–10 minutes of" +
                "deep breathing, meditation, or gentle yoga can help" +
                "lower stress hormones."

    minutes < goal ->
        "Nice start! ${goal - minutes} more minutes gets you to today's mindfulness goal."

    else ->
        "You've hit your mindfulness goal today. Calm moments like this support hormone balance — keep it going."
}