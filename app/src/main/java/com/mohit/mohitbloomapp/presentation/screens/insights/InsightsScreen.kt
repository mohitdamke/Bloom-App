package com.mohit.mohitbloomapp.presentation.screens.insights

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bedtime
import androidx.compose.material.icons.outlined.Mood
import androidx.compose.material.icons.outlined.Spa
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mohit.mohitbloomapp.ui.theme.BloomColors

@Composable
fun InsightsScreen() {
    BloomInsightsScreen()
}

@Composable
fun BloomInsightsScreen() {
    var period by rememberSaveable { mutableStateOf("Week") }
    val scores = if (period == "Week") listOf(64, 90, 72, 82, 84, 88, 12) else listOf(42, 55, 70, 61, 85, 77, 65)
    val weekdays = listOf("M", "T", "W", "T", "F", "S", "S")
    val symptoms = listOf(
        Triple("Bloating", "Moderate or higher on 5 of 7 days", Icons.Outlined.WaterDrop),
        Triple("Skin & acne", "Moderate or higher on 2 of 7 days", Icons.Outlined.Spa),
        Triple("Mood", "Moderate or higher on 2 of 7 days", Icons.Outlined.Mood),
        Triple("Sleep quality", "Moderate or higher on 2 of 7 days", Icons.Outlined.Bedtime)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BloomColors.Background)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 18.dp)
            .padding(top = 26.dp, bottom = 24.dp)
    ) {
        // Screen Header
        Text(
            text = "Insights",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = BloomColors.Text
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Track your patterns & well-being over time",
            fontSize = 14.sp,
            color = BloomColors.Muted
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Period Toggle (Week vs Month)
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(BloomColors.Sand)
                .padding(4.dp)
        ) {
            listOf("Week", "Month").forEach { p ->
                val isSelected = period == p
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(if (isSelected) BloomColors.Primary else BloomColors.Sand)
                        .clickable { period = p }
                        .padding(horizontal = 20.dp, vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = p,
                        fontSize = 13.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        color = if (isSelected) MaterialTheme.colorScheme.surface else BloomColors.Text
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Score Bar Chart Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(containerColor = BloomColors.Card),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Wellness Score",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = BloomColors.Text
                        )
                        Text(
                            text = "Average ${scores.average().toInt()}%",
                            fontSize = 12.sp,
                            color = BloomColors.Muted
                        )
                    }
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = BloomColors.SoftPink.copy(alpha = 0.3f)
                    ) {
                        Text(
                            text = period,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = BloomColors.Primary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Bar Chart
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    scores.forEachIndexed { index, score ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Bottom,
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = "$score",
                                fontSize = 10.sp,
                                color = BloomColors.Muted
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Box(
                                modifier = Modifier
                                    .width(18.dp)
                                    .height((score * 0.9).dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(
                                        if (score >= 80) BloomColors.Primary
                                        else if (score >= 50) BloomColors.SoftPink
                                        else BloomColors.Gold
                                    )
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = weekdays.getOrElse(index) { "" },
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = BloomColors.Text
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Symptoms & Observations Title
        Text(
            text = "Symptoms & Observations",
            fontSize = 17.sp,
            fontWeight = FontWeight.SemiBold,
            fontStyle = FontStyle.Italic,
            color = BloomColors.Text
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Symptoms List
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            symptoms.forEach { (title, subtitle, icon) ->
                SymptomCard(title = title, subtitle = subtitle, icon = icon)
            }
        }
    }
}

@Composable
private fun SymptomCard(
    title: String,
    subtitle: String,
    icon: ImageVector
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = BloomColors.Card),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(BloomColors.SoftPink.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = BloomColors.Primary,
                    modifier = Modifier.size(22.dp)
                )
            }
            Spacer(modifier = Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = BloomColors.Text
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = subtitle,
                    fontSize = 12.sp,
                    color = BloomColors.Muted
                )
            }
        }
    }
}
