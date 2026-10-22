package com.mohit.mohitbloomapp.presentation.screens.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Remove
import androidx.compose.material.icons.outlined.WaterDrop
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
fun BloomLogWaterSheet(
    waterGlasses: Int,
    goalGlasses: Int = 8,
    onGlassesChange: (Int) -> Unit,
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
                    text = "Log Water Intake",
                    modifier = Modifier.weight(1f),
                    color = BloomColors.Text,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic
                )
                IconButton(onClick = onDismiss) {
                    Icon(Icons.Outlined.Close, contentDescription = "Close", tint = BloomColors.Muted)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(BloomColors.Indigo.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.WaterDrop,
                    contentDescription = null,
                    tint = BloomColors.Indigo,
                    modifier = Modifier.size(32.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "$waterGlasses / $goalGlasses glasses",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = BloomColors.Text
            )

            Text(
                text = "Daily Water Goal: $goalGlasses glasses",
                fontSize = 13.sp,
                color = BloomColors.Muted
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                IconButton(
                    onClick = { if (waterGlasses > 0) onGlassesChange(waterGlasses - 1) },
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(BloomColors.Sand)
                ) {
                    Icon(Icons.Outlined.Remove, contentDescription = "Decrease", tint = BloomColors.Primary)
                }

                Button(
                    onClick = { onGlassesChange(waterGlasses + 1) },
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = BloomColors.Primary)
                ) {
                    Text("+1 glass", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                }

                IconButton(
                    onClick = { onGlassesChange(waterGlasses + 1) },
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(BloomColors.Sand)
                ) {
                    Icon(Icons.Outlined.Add, contentDescription = "Increase", tint = BloomColors.Primary)
                }
            }
        }
    }
}
