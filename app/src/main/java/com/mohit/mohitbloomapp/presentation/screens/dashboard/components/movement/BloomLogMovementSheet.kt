package com.mohit.mohitbloomapp.presentation.screens.dashboard.components.movement

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mohit.mohitbloomapp.ui.theme.BloomColors
import com.mohit.mohitbloomapp.ui.theme.Fraunces

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BloomLogMovementSheet(
    movementMinutes: Int,
    goalMinutes: Int = 30,
    onMinutesChange: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        containerColor = BloomColors.Background,
        contentColor = BloomColors.Text,
        tonalElevation = 0.dp,
        dragHandle = {
            Box(
                modifier = Modifier
                    .padding(top = 9.dp, bottom = 5.dp)
                    .size(width = 48.dp, height = 5.dp)
                    .clip(CircleShape)
                    .background(BloomColors.SoftPink)
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(horizontal = 24.dp)
                .padding(top = 12.dp, bottom = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Log Movement",
                    modifier = Modifier.weight(1f),
                    fontSize = 22.sp,
                    fontFamily = Fraunces,
                    fontWeight = W600,
                    color = BloomColors.Text
                )
                IconButton(onClick = onDismiss) {
                    Icon(Icons.Outlined.Close, contentDescription = "Close", tint = BloomColors.Muted)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))


            MovementTracker(
                minutes = movementMinutes,
                goalMinutes = goalMinutes,
                onDecrement = { onMinutesChange((movementMinutes - 5).coerceAtLeast(0)) },
                onIncrement = { onMinutesChange(movementMinutes + 5) },
                onQuickAdd = { onMinutesChange(movementMinutes + 10) },
                onSettingsClick = { /* open settings */ }
            )
    }}
}
