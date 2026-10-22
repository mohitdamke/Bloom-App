package com.mohit.mohitbloomapp.presentation.screens.dashboard.components.water

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Remove
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Text
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
fun BloomLogWaterSheet(
    waterGlasses: Int,
    goalGlasses: Int = 8,
    onGlassesChange: (Int) -> Unit,
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
                    text = "Log Water Intake",
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

            // ---------- Same layout as the movement tracker ----------
            BloomText(text = "Today's water", style = BloomTextStyle.Label)

            Spacer(modifier = Modifier.height(12.dp))

            BloomStepper(
                value = "$waterGlasses",
                unit = if (waterGlasses == 1) "glass" else "glasses",
                minusIcon = Icons.Outlined.Remove,
                plusIcon = Icons.Outlined.Add,
                onMinus = { if (waterGlasses > 0) onGlassesChange(waterGlasses - 1) },
                onPlus = { onGlassesChange(waterGlasses + 1) }
            )

            Spacer(modifier = Modifier.height(14.dp))

            BloomLinkText(
                prefix = "Goal: $goalGlasses glasses · adjust in ",
                linkText = "Settings",
                onLinkClick = onSettingsClick
            )

            Spacer(modifier = Modifier.height(16.dp))

            BloomInfoCard(
                title = waterTipTitle(waterGlasses, goalGlasses),
                body = waterTipBody(waterGlasses, goalGlasses),
                icon = Icons.Outlined.WaterDrop,
                action = {
                    BloomPillButton(
                        text = "+1 glass",
                        onClick = { onGlassesChange(waterGlasses + 1) }
                    )
                }
            )
        }
    }
}

private fun waterTipTitle(glasses: Int, goal: Int) = when {
    glasses == 0 -> "Start hydrating"
    glasses >= goal -> "Goal reached!"
    else -> "Keep sipping"
}

private fun waterTipBody(glasses: Int, goal: Int) = when {
    glasses == 0 ->
        "No water logged yet today. Try starting with a glass" +
                "first thing in the morning."
    glasses < goal ->
        "Nice! ${goal - glasses} more ${if (goal - glasses == 1) "glass" else "glasses"} to reach today's goal. Keep a bottle nearby and sip through the day."
    else ->
        "You've hit your water goal today. Great job — keep listening to your body and drink more if you're active or it's hot."
}