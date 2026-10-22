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
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.Bedtime
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.Mood
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

/** One row in "Symptoms & Observations".
 *  [weekLevels] = one severity level (0..4) per day, 0 = none/lightest, 4 = severe/darkest. */
private data class SymptomSummary(
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
    val weekLevels: List<Int>
)

/** Light -> dark shades used by the mini bars (level 0..4). */
private val SeverityColors = listOf(
    Color(0xFFF4E2DE), // 0 - lightest
    Color(0xFFE8C6C0), // 1
    Color(0xFFD9A39B), // 2
    Color(0xFFB3665F), // 3
    Color(0xFF7A3A36)  // 4 - darkest
)

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
        SymptomSummary("Bloating", "Moderate or higher on 5 of 7 days", Icons.Outlined.WaterDrop, listOf(2, 2, 4, 2, 2, 4, 4)),
        SymptomSummary("Skin & acne", "Moderate or higher on 2 of 7 days", Icons.Outlined.AutoAwesome, listOf(1, 2, 2, 2, 1, 3, 3)),
        SymptomSummary("Mood", "Moderate or higher on 2 of 7 days", Icons.Outlined.Mood, listOf(1, 1, 2, 2, 3, 3, 2)),
        SymptomSummary("Sleep quality", "Moderate or higher on 2 of 7 days", Icons.Outlined.Bedtime, listOf(2, 2, 1, 2, 3, 2, 4))
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

        BloomSectionTitle(title = "Cycle")
        Spacer(modifier = Modifier.height(6.dp))

        QuickActionCard(
            title = "No cycle logged yet",
            subtitle = "Log your last period date",
            icon = Icons.Filled.WaterDrop,
            tint = White
        )
        Spacer(modifier = Modifier.height(18.dp))

        BloomSectionTitle(title = "Daily summary")
        Spacer(modifier = Modifier.height(6.dp))
        ProgressInfoCard()
        Spacer(modifier = Modifier.height(18.dp))

        BloomSectionTitle(title = "Symptoms & Observations")

        Spacer(modifier = Modifier.height(12.dp))

        // Symptoms List
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            symptoms.forEach { symptom ->
                SymptomCard(
                    title = symptom.title,
                    subtitle = symptom.subtitle,
                    icon = symptom.icon,
                    weekLevels = symptom.weekLevels
                )
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

/** Symptom row: rounded-square icon, title + subtitle, and a 7-day severity bar strip on the right. */
@Composable
private fun SymptomCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    weekLevels: List<Int>
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = BloomColors.Card),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Rounded-square icon box
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(SoftPink.copy(alpha = 0.3f)),
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
                    fontSize = 16.sp,
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

            Spacer(modifier = Modifier.width(10.dp))

            // The colourful 7-day strip
            SeverityBars(levels = weekLevels)
        }
    }
}

/** Mini bar strip: one rounded bar per day, shade = severity level (0 light .. 4 dark). */
@Composable
private fun SeverityBars(
    levels: List<Int>,
    modifier: Modifier = Modifier,
    barWidth: Dp = 6.dp,
    barHeight: Dp = 30.dp,
    spacing: Dp = 4.dp
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(spacing),
        verticalAlignment = Alignment.CenterVertically
    ) {
        levels.forEach { level ->
            Box(
                modifier = Modifier
                    .width(barWidth)
                    .height(barHeight)
                    .clip(RoundedCornerShape(50))
                    .background(SeverityColors[level.coerceIn(0, SeverityColors.lastIndex)])
            )
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