package com.mohit.mohitbloomapp.presentation.screens.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Bedtime
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mohit.mohitbloomapp.ui.theme.BloomColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BloomLogSleepSheet(
    sleepHours: Int,
    goalHours: Int = 8,
    onSleepHoursChange: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(
            topStart = 24.dp,
            topEnd = 24.dp
        ),
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
                    text = "Log Sleep",
                    modifier = Modifier.weight(1f),
                    color = BloomColors.Text,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic
                )

                IconButton(onClick = onDismiss) {
                    Icon(
                        imageVector = Icons.Outlined.Close,
                        contentDescription = "Close",
                        tint = BloomColors.Muted
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(BloomColors.Primary.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Bedtime,
                    contentDescription = "Sleep",
                    tint = BloomColors.Primary,
                    modifier = Modifier.size(32.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "$sleepHours / $goalHours hours",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = BloomColors.Text
            )

            Text(
                text = "Last night's sleep",
                fontSize = 13.sp,
                color = BloomColors.Muted
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                IconButton(
                    onClick = {
                        onSleepHoursChange(
                            (sleepHours - 1).coerceAtLeast(0)
                        )
                    },
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(BloomColors.Sand)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Remove,
                        contentDescription = "Decrease sleep hours",
                        tint = BloomColors.Primary
                    )
                }

                Button(
                    onClick = {
                        onSleepHoursChange(
                            (sleepHours + 1).coerceAtMost(24)
                        )
                    },
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = BloomColors.Primary
                    )
                ) {
                    Text(
                        "+1 hour",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                IconButton(
                    onClick = {
                        onSleepHoursChange(
                            (sleepHours + 1).coerceAtMost(24)
                        )
                    },
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(BloomColors.Sand)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Add,
                        contentDescription = "Increase sleep hours",
                        tint = BloomColors.Primary
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedButton(
                onClick = {
                    onSleepHoursChange(8)
                },
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Log 8 hours",
                    color = BloomColors.Text
                )
            }
        }
    }
}
