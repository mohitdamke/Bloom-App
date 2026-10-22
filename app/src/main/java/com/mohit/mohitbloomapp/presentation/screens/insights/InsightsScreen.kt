package com.mohit.mohitbloomapp.presentation.screens.insights

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.outlined.Bedtime
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.Mood
import androidx.compose.material.icons.outlined.Spa
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mohit.mohitbloomapp.ui.theme.BloomColors
import com.mohit.mohitbloomapp.ui.theme.BloomColors.SoftPink
import com.mohit.mohitbloomapp.ui.theme.Fraunces
import com.mohit.mohitbloomapp.ui.theme.PlusJakartaSans

@Composable
fun InsightsScreen() {
    BloomInsightsScreen()
}

@Composable
fun BloomInsightsScreen() {
    var period by rememberSaveable { mutableStateOf("Week") }

    val scores = if (period == "Week") {
        listOf(64, 90, 72, 82, 84, 88, 12)
    } else {
        listOf(42, 55, 70, 61, 85, 77, 65)
    }
    val labels = if (period == "Week") {
        listOf("M", "T", "W", "T", "F", "S", "S")
    } else {
        listOf("1", "5", "10", "15", "20", "25", "30")
    }

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
            fontWeight = FontWeight.W800,
            fontFamily = Fraunces,
            color = BloomColors.Text
        )
        Text(
            text = "Your week at a glance, and what to try next",
            fontSize = 12.sp,
            fontWeight = FontWeight.W400,
            fontFamily = PlusJakartaSans,
            color = BloomColors.Muted
        )
        Spacer(modifier = Modifier.height(20.dp))

        BloomSectionTitle(title = "Your score")
        Spacer(modifier = Modifier.height(12.dp))

        // Score card (includes the Week / Month toggle)
        WellnessScoreCard(
            scores = scores,
            weekdays = labels,
            period = period,
            onPeriodChange = { period = it },
            todayIndex = scores.lastIndex,
            onDayClick = { index ->
                // TODO: show the summary for the tapped day
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Symptoms & Observations Title
        BloomSectionTitle(title = "Cycle")
        Spacer(modifier = Modifier.height(6.dp))

        QuickActionCard(
            title = "No cycle logged yet",
            subtitle = "Log your last period date",
            icon = Icons.Filled.WaterDrop,
            tint = White
        )
        Spacer(modifier = Modifier.height(18.dp))

        // Symptoms & Observations Title
        BloomSectionTitle(title = "Daily summary")
        Spacer(modifier = Modifier.height(6.dp))
        ProgressInfoCard()
        Spacer(modifier = Modifier.height(18.dp))

        // Symptoms & Observations Title
        BloomSectionTitle(title = "Symptoms & Observations")

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
private fun WellnessScoreCard(
    scores: List<Int>,
    weekdays: List<String>,
    period: String,
    onPeriodChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    todayIndex: Int = scores.lastIndex,
    onDayClick: (Int) -> Unit = {}
) {
    var selectedIndex by remember(period, scores) { mutableStateOf(todayIndex) }
    val average = if (scores.isEmpty()) 0 else scores.average().toInt()
    val periodLabel = if (period == "Week") "week average" else "month average"

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = BloomColors.Card),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 22.dp)
        ) {
            // ---------- Header: average + toggle ----------
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column {
                    Text(
                        text = "$average%",
                        fontSize = 30.sp,
                        fontFamily = Fraunces,
                        fontWeight = FontWeight.W800,
                        color = BloomColors.Primary
                    )
                    Text(
                        text = periodLabel,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.W400,
                        fontFamily = PlusJakartaSans,
                        color = BloomColors.Muted
                    )
                }

                PeriodToggle(
                    period = period,
                    onPeriodChange = onPeriodChange
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // ---------- Bars ----------
            val maxBarHeight = 130.dp
            val minBarHeight = 14.dp

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                scores.forEachIndexed { index, score ->
                    val isSelected = index == selectedIndex
                    val isToday = index == todayIndex

                    val targetHeight: Dp =
                        (maxBarHeight * (score.coerceIn(0, 100) / 100f)).coerceAtLeast(minBarHeight)
                    val barHeight by animateDpAsState(
                        targetValue = targetHeight,
                        label = "barHeight"
                    )

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                selectedIndex = index
                                onDayClick(index)
                            },
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        // Fixed-height area so all bars sit on the same baseline
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(maxBarHeight),
                            contentAlignment = Alignment.BottomCenter
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(barHeight)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(
                                        if (isSelected) BloomColors.Primary
                                        else BloomColors.SoftPink.copy(alpha = 0.55f)
                                    )
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = weekdays.getOrElse(index) { "" },
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = PlusJakartaSans,
                            textAlign = TextAlign.Center,
                            color = if (isSelected) BloomColors.Primary else BloomColors.Muted
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        // "Today" dot (transparent placeholder keeps alignment)
                        Box(
                            modifier = Modifier
                                .size(5.dp)
                                .clip(CircleShape)
                                .background(if (isToday) BloomColors.Primary else Color.Transparent)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            // ---------- Hint ----------
            Text(
                text = "Tap a day to see its summary",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 12.sp,
                fontWeight = FontWeight.W400,
                fontFamily = PlusJakartaSans,
                color = BloomColors.Muted
            )
        }
    }
}

@Composable
private fun PeriodToggle(
    period: String,
    onPeriodChange: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(BloomColors.SoftPink.copy(alpha = 0.25f))
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        listOf("Week", "Month").forEach { p ->
            val isSelected = period == p
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(if (isSelected) BloomColors.Primary else Color.Transparent)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { onPeriodChange(p) }
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = p,
                    fontSize = 14.sp,
                    fontWeight = if (isSelected) FontWeight.W800 else FontWeight.W500,
                    fontFamily = PlusJakartaSans,
                    color = if (isSelected) Color.White else BloomColors.Muted
                )
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
                    fontFamily = PlusJakartaSans,
                    color = BloomColors.Text
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = subtitle,
                    fontSize = 12.sp,
                    fontFamily = PlusJakartaSans,
                    color = BloomColors.Muted
                )
            }
        }
    }
}

@Composable
private fun BloomSectionTitle(title: String) {
    Text(
        text = title,
        fontSize = 18.sp,
        fontWeight = FontWeight.W600,
        fontStyle = FontStyle.Italic,
        fontFamily = Fraunces,
        color = BloomColors.Text,
    )
}


@Composable
private fun QuickActionCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    tint: Color,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = tint
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp,
                    vertical = 12.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(43.dp)
                    .clip(RoundedCornerShape(100.dp))
                    .background(SoftPink),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    icon,
                    contentDescription = null,
                    tint = BloomColors.Primary,
                    modifier = Modifier.size(22.dp)
                )
            }

            Spacer(Modifier.width(12.dp))

            Column(Modifier.weight(1f)) {
                Text(
                    title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = BloomColors.Text
                )

                Text(
                    subtitle,
                    fontSize = 11.sp,
                    color = BloomColors.Muted,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Icon(
                imageVector = Icons.Outlined.ChevronRight,
                contentDescription = null,
                tint = SoftPink
            )
        }
    }
}

@Composable
private fun ProgressInfoCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = BloomColors.Card
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left rounded square
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(BloomColors.PrimaryDark),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "0%",
                    fontSize = 14.sp,
                    fontFamily = PlusJakartaSans,
                    fontWeight = FontWeight.W800,
                    color = SoftPink
                )
            }

            Spacer(Modifier.width(16.dp))

            // Right side: two texts
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Your Wellness Progress",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = BloomColors.Text
                )

                Spacer(Modifier.height(5.dp))

                Text(
                    text = "Keep tracking your daily goals!",
                    fontSize = 12.sp,
                    color = BloomColors.Muted
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun InsightsScreenPreview() {
    MaterialTheme {
        BloomInsightsScreen()
    }
}