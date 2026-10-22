package com.mohit.mohitbloomapp.presentation.screens.dashboard

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.DirectionsRun
import androidx.compose.material.icons.filled.Mood
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.outlined.Bedtime
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.NightsStay
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.material.icons.outlined.Spa
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mohit.mohitbloomapp.R
import com.mohit.mohitbloomapp.presentation.screens.dashboard.components.cycle.BloomLogCycleSheet
import com.mohit.mohitbloomapp.presentation.screens.dashboard.components.meal.BloomLogMealsSheet
import com.mohit.mohitbloomapp.presentation.screens.dashboard.components.movement.BloomLogMovementSheet
import com.mohit.mohitbloomapp.presentation.screens.dashboard.components.relax.BloomLogRelaxationSheet
import com.mohit.mohitbloomapp.presentation.screens.dashboard.components.sleep.BloomLogSleepSheet
import com.mohit.mohitbloomapp.presentation.screens.dashboard.components.symptons.BloomLogSymptomsSheet
import com.mohit.mohitbloomapp.presentation.screens.dashboard.components.water.BloomLogWaterSheet
import com.mohit.mohitbloomapp.ui.theme.BloomColors
import com.mohit.mohitbloomapp.ui.theme.Fraunces
import com.mohit.mohitbloomapp.ui.theme.PlusJakartaSans
import kotlinx.coroutines.launch
import java.time.LocalDate

data class BloomGoal(
    val title: String,
    val icon: ImageVector,
    val color: Color
)

@Composable
fun DashboardScreen(
    onGoalClick: (String) -> Unit = {}
) {
    val faqQuestions = listOf("Card 1", "Card 2", "Card 3")

    val bloomGoals = listOf(
        BloomGoal("Meals", Icons.Outlined.Restaurant, BloomColors.Gold),
        BloomGoal(
            "Movement",
            Icons.AutoMirrored.Outlined.DirectionsRun,
            BloomColors.Green
        ),
        BloomGoal("Water", Icons.Outlined.WaterDrop, BloomColors.Indigo),
        BloomGoal("Relaxation", Icons.Outlined.Spa, BloomColors.Primary),
        BloomGoal("Sleep", Icons.Outlined.NightsStay, BloomColors.PrimaryDark)
    )

    var showTip by rememberSaveable { mutableStateOf(true) }
    var selectedSymptoms by remember { mutableStateOf<Map<String, String>>(emptyMap()) }

    var showMealLog by rememberSaveable { mutableStateOf(false) }
    var showMovementLog by rememberSaveable { mutableStateOf(false) }
    var showWaterLog by rememberSaveable { mutableStateOf(false) }
    var showRelaxationLog by rememberSaveable { mutableStateOf(false) }
    var showSleepLog by rememberSaveable { mutableStateOf(false) }

    // New bottom sheet states
    var showCycleSheet by rememberSaveable { mutableStateOf(false) }
    var showSymptomsSheet by rememberSaveable { mutableStateOf(false) }

    var movementMinutes by rememberSaveable { mutableIntStateOf(0) }
    var waterGlasses by rememberSaveable { mutableIntStateOf(0) }
    var relaxationMinutes by rememberSaveable { mutableIntStateOf(0) }
    var sleepHours by rememberSaveable { mutableIntStateOf(0) }

    // Cycle and symptom data
    var lastPeriodDate by rememberSaveable {
        mutableStateOf<String?>(null)
    }

    var breakfastChoice by rememberSaveable {
        mutableStateOf<String?>(null)
    }

    var lunchChoice by rememberSaveable {
        mutableStateOf<String?>(null)
    }

    var dinnerChoice by rememberSaveable {
        mutableStateOf<String?>(null)
    }

    val mealSelections = remember(
        breakfastChoice,
        lunchChoice,
        dinnerChoice
    ) {
        buildMap {
            breakfastChoice?.let { put("Breakfast", it) }
            lunchChoice?.let { put("Lunch", it) }
            dinnerChoice?.let { put("Dinner", it) }
        }
    }

    val mealsLogged = mealSelections.size

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BloomColors.Background)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 18.dp)
            .padding(top = 20.dp, bottom = 24.dp)
    ) {

// Top greeting
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "GOOD MORNING,",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W700,
                    color = BloomColors.Muted,
                    fontFamily = PlusJakartaSans,
                    letterSpacing = 1.sp
                )

                Spacer(Modifier.width(6.dp))

                Text(
                    text = "Raees",
                    fontSize = 22.sp,
                    fontFamily = Fraunces,
                    fontWeight = FontWeight.W600,
                    fontStyle = FontStyle.Italic,
                    color = BloomColors.Text
                )
            }

            Image(
                painter = painterResource(id = R.drawable.person),
                contentDescription = "Profile",
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
            )
        }


        Spacer(Modifier.height(20.dp))

// Small hero card
        TodayGoalsHero(
            date = LocalDate.now().format(
                java.time.format.DateTimeFormatter.ofPattern("EEEE, MMM d")
            ),
            onClick = {}
        )

        Spacer(Modifier.height(24.dp))

        BloomSectionTitle("PCOS FAQ")

        Spacer(Modifier.height(12.dp))

        PcosFaqSlider()

        Spacer(Modifier.height(10.dp))

        // Cycle card
        QuickActionCard(
            title = "Cycle",
            subtitle = "Log your last period date",
            icon = Icons.Filled.WaterDrop,
            tint = BloomColors.PalePink
        ) {
            showCycleSheet = true
        }

        Spacer(Modifier.height(9.dp))

        // Symptom check-in card
        QuickActionCard(
            title = "Symptom check-in",
            subtitle = "Bloating, skin, mood, sleep quality",
            icon = Icons.Filled.Mood,
            tint = BloomColors.PalePink
        ) {
            showSymptomsSheet = true
        }

        Spacer(Modifier.height(20.dp))

        BloomSectionTitle("Goal Progress")

        Spacer(Modifier.height(10.dp))

        bloomGoals.forEach { goal ->

            GoalProgressCard(
                goal = goal,
                progressOverride = when (goal.title) {
                    "Meals" -> "$mealsLogged/3 meals"
                    "Movement" -> "$movementMinutes/30 min"
                    "Water" -> "$waterGlasses/8 glasses"
                    "Relaxation" -> "$relaxationMinutes/15 min"
                    "Sleep" -> "$sleepHours/8 hrs"
                    else -> null
                },
                onClick = {
                    when (goal.title) {
                        "Meals" -> showMealLog = true
                        "Movement" -> showMovementLog = true
                        "Water" -> showWaterLog = true
                        "Relaxation" -> showRelaxationLog = true
                        "Sleep" -> showSleepLog = true
                        else -> onGoalClick(goal.title)
                    }
                }
            )

            Spacer(Modifier.height(9.dp))
        }

        Spacer(Modifier.height(11.dp))

        BloomSectionTitle("Today's tip")

        Spacer(Modifier.height(10.dp))

        if (showTip) {
            TipCard(
                onRemindLater = {
                    showTip = false
                },
                onMarkDone = {
                    showTip = false
                }
            )
        }
    }

    // Meals bottom sheet
    if (showMealLog) {
        BloomLogMealsSheet(
            selectedMeals = mealSelections,
            onSelectMeal = { meal, choice ->
                when (meal) {
                    "Breakfast" -> breakfastChoice = choice
                    "Lunch" -> lunchChoice = choice
                    "Dinner" -> dinnerChoice = choice
                }
            },
            onDismiss = {
                showMealLog = false
            }
        )
    }

    // Movement bottom sheet
    if (showMovementLog) {
        BloomLogMovementSheet(
            movementMinutes = movementMinutes,
            goalMinutes = 30,
            onMinutesChange = {
                movementMinutes = it
            },
            onDismiss = {
                showMovementLog = false
            }
        )
    }

    // Water bottom sheet
    if (showWaterLog) {
        BloomLogWaterSheet(
            waterGlasses = waterGlasses,
            goalGlasses = 8,
            onGlassesChange = {
                waterGlasses = it.coerceIn(0, 30)
            },
            onDismiss = {
                showWaterLog = false
            }
        )
    }

    // Relaxation bottom sheet
    if (showRelaxationLog) {
        BloomLogRelaxationSheet(
            relaxationMinutes = relaxationMinutes,
            goalMinutes = 15,
            onMinutesChange = {
                relaxationMinutes = it.coerceIn(0, 300)
            },
            onDismiss = {
                showRelaxationLog = false
            }
        )
    }

    // Sleep bottom sheet
    if (showSleepLog) {
        BloomLogSleepSheet(
            sleepHours = sleepHours,
            goalHours = 8,
            onSleepHoursChange = {
                sleepHours = it.coerceIn(0, 24)
            },
            onDismiss = {
                showSleepLog = false
            }
        )
    }

    // Cycle bottom sheet
    if (showCycleSheet) {
        BloomLogCycleSheet(
            initialDate = lastPeriodDate
                ?.let {
                    runCatching {
                        LocalDate.parse(it)
                    }.getOrNull()
                }
                ?: LocalDate.now(),
            onDone = { date ->
                lastPeriodDate = date.toString()
                showCycleSheet = false
            },
            onDismiss = {
                showCycleSheet = false
            }
        )
    }


// after
    // Symptom check-in bottom sheet
    if (showSymptomsSheet) {
        BloomLogSymptomsSheet(
            selectedSymptoms = selectedSymptoms,
            onDone = { symptoms ->
                selectedSymptoms = symptoms
                showSymptomsSheet = false
            },
            onDismiss = {
                showSymptomsSheet = false
            }
        )
    }
}

@Composable
private fun TodayGoalsHero(
    date: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(152.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFF8F6)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 7.dp
        )
    ) {
        Box(Modifier.fillMaxSize()) {

            Image(
                painter = painterResource(id = R.drawable.flower),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .size(150.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        horizontal = 20.dp,
                        vertical = 20.dp
                    )
            ) {
                Text(
                    text = "Today's Goals",
                    fontSize = 21.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontStyle = FontStyle.Italic,
                    color = BloomColors.PrimaryDark
                )

                Text(
                    date,
                    fontSize = 12.sp,
                    color = BloomColors.Muted
                )

                Spacer(Modifier.height(6.dp))

                Text(
                    text = "Let's get blooming",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontStyle = FontStyle.Italic,
                    color = BloomColors.Text
                )

                Text(
                    "0 of 5 complete",
                    fontSize = 12.sp,
                    color = BloomColors.Muted
                )

                Spacer(Modifier.height(5.dp))

                Box(
                    modifier = Modifier
                        .width(150.dp)
                        .height(4.dp)
                        .clip(CircleShape)
                        .background(BloomColors.SoftPink)
                )
            }
        }
    }
}

@Composable
private fun BloomBlossom(
    modifier: Modifier = Modifier,
    alpha: Float = 1f
) {
    Canvas(modifier = modifier) {

        val petalColor = BloomColors.SoftPink.copy(alpha = alpha)
        val center = Offset(
            size.width / 2f,
            size.height / 2f
        )

        val petalW = size.width * 0.27f
        val petalH = size.height * 0.42f

        listOf(0f, 72f, 144f, 216f, 288f).forEach { angle ->

            rotate(angle, pivot = center) {
                drawOval(
                    color = petalColor,
                    topLeft = Offset(
                        center.x - petalW / 2f,
                        center.y - size.height * 0.43f
                    ),
                    size = Size(petalW, petalH)
                )
            }
        }

        drawCircle(
            color = BloomColors.Primary.copy(alpha = alpha),
            radius = size.minDimension * 0.10f,
            center = center
        )
    }
}

@Composable
private fun QuickActionCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    tint: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
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
                    .clip(RoundedCornerShape(14.dp))
                    .background(Color.White),
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
                Icons.Outlined.ChevronRight,
                contentDescription = null,
                tint = BloomColors.PrimaryDark
            )
        }
    }
}

@Composable
private fun GoalProgressCard(
    goal: BloomGoal,
    progressOverride: String? = null,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(21.dp),
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
                .padding(
                    horizontal = 16.dp,
                    vertical = 10.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .border(
                        4.dp,
                        goal.color,
                        CircleShape
                    )
                    .padding(9.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    goal.icon,
                    contentDescription = null,
                    tint = goal.color,
                    modifier = Modifier.size(21.dp)
                )
            }

            Spacer(Modifier.width(15.dp))

            Column(Modifier.weight(1f)) {

                Text(
                    goal.title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W800,
                    fontFamily = PlusJakartaSans,
                    color = BloomColors.Text
                )

                val progressText = when (goal.title) {
                    "Meals" -> "0/3 meals"
                    "Movement" -> "0/30 min"
                    "Water" -> "0/8 glasses"
                    "Relaxation" -> "0/15 min"
                    else -> "0/8 hrs"
                }

                Text(
                    progressOverride ?: progressText,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W600,
                    fontFamily = PlusJakartaSans,
                    color = BloomColors.Muted
                )
            }

            Icon(
                Icons.Outlined.ChevronRight,
                contentDescription = null,
                tint = BloomColors.Muted
            )
        }
    }
}

@Composable
private fun TipCard(
    onRemindLater: () -> Unit,
    onMarkDone: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = BloomColors.Sand
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.Top
            ) {
                Box(
                    modifier = Modifier
                        .size(43.dp)
                        .clip(RoundedCornerShape(13.dp))
                        .background(Color(0xFFE5E3EE)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Outlined.Bedtime,
                        contentDescription = null,
                        tint = BloomColors.Indigo
                    )
                }

                Spacer(Modifier.width(12.dp))

                Column(Modifier.weight(1f)) {
                    Text(
                        "LOG LAST NIGHT'S SLEEP",
                        fontSize = 12.sp,
                        color = BloomColors.Gold,
                        fontWeight = FontWeight.W800,
                        fontFamily = PlusJakartaSans,
                        letterSpacing = 0.3.sp
                    )

                    Spacer(Modifier.height(4.dp))

                    Text(
                        "You haven't logged sleep yet. Consistent, close-to-8-hour nights can support wellbeing — try avoiding screens before bed if that helps you wind down.",
                        fontSize = 13.sp,
                        lineHeight = 19.sp,
                        color = BloomColors.Text,
                        fontFamily = PlusJakartaSans,
                        fontWeight = FontWeight.W400,
                    )
                }
            }

            Spacer(Modifier.height(10.dp))

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(
                    onClick = onRemindLater
                ) {
                    Text(
                        "Remind me later",
                        color = BloomColors.Text,
                        fontSize = 12.sp,
                        fontFamily = PlusJakartaSans,
                        fontWeight = FontWeight.W600
                    )
                }

                Spacer(Modifier.width(5.dp))

                Button(
                    onClick = onMarkDone,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = BloomColors.Green
                    ),
                    shape = RoundedCornerShape(22.dp),
                    contentPadding = PaddingValues(
                        horizontal = 13.dp,
                        vertical = 8.dp
                    )
                ) {
                    Icon(
                        Icons.Outlined.Check,
                        contentDescription = null,
                        modifier = Modifier.size(15.dp), tint = White
                    )

                    Spacer(Modifier.width(4.dp))

                    Text(
                        "Mark as done",
                        fontSize = 12.sp,
                        color = White,
                        fontFamily = PlusJakartaSans,
                        fontWeight = FontWeight.W600
                    )
                }
            }
        }
    }
}

private val defaultFaqItems = listOf(
    "What is PCOS?" to "Understand PCOS and its common symptoms.",
    "Can diet help?" to "Learn how balanced meals support wellbeing.",
    "How can I manage stress?" to "Explore simple ways to relax every day."
)

/** Swipeable FAQ cards with page dots that follow the current page. */
@Composable
fun PcosFaqSlider(
    modifier: Modifier = Modifier,
    faqItems: List<Pair<String, String>> = defaultFaqItems
) {
    val pagerState = rememberPagerState(pageCount = { faqItems.size })

    Column(modifier = modifier.fillMaxWidth()) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth(),
            // small end padding lets the next card peek in; remove for full-width cards
            contentPadding = PaddingValues(end = 32.dp),
            pageSpacing = 12.dp
        ) { page ->
            val (title, description) = faqItems[page]
            FaqCard(title = title, description = description)
        }

        Spacer(Modifier.height(12.dp))

        PagerDots(
            pagerState = pagerState,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
private fun FaqCard(title: String, description: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 80.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = BloomColors.Card),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                fontSize = 15.sp,
                fontWeight = FontWeight.W800,
                fontFamily = PlusJakartaSans,
                color = BloomColors.Text
            )

            Spacer(Modifier.height(4.dp))

            Text(
                text = description,
                fontSize = 12.sp,
                fontWeight = FontWeight.W400,
                fontFamily = PlusJakartaSans,
                lineHeight = 16.sp,   // was 2.sp, which squashed the lines together
                color = BloomColors.Muted
            )
        }
    }
}

/** Dots that animate (width + color) as the pager moves; tap a dot to jump to that page. */
@Composable
private fun PagerDots(
    pagerState: PagerState,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(pagerState.pageCount) { index ->
            val isSelected = pagerState.currentPage == index

            val width by animateDpAsState(
                targetValue = if (isSelected) 20.dp else 8.dp,
                label = "dotWidth"
            )
            val color by animateColorAsState(
                targetValue = if (isSelected) BloomColors.Primary
                else BloomColors.SoftPink.copy(alpha = 0.6f),
                label = "dotColor"
            )

            Box(
                modifier = Modifier
                    .height(8.dp)
                    .width(width)
                    .clip(CircleShape)
                    .background(color)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        scope.launch { pagerState.animateScrollToPage(index) }
                    }
            )
        }
    }
}

@Composable
private fun BloomSectionTitle(title: String) {
    Text(
        title,
        fontSize = 18.sp,
        fontWeight = FontWeight.W600,
        fontStyle = FontStyle.Italic,
        fontFamily = Fraunces,
        color = BloomColors.Text,
    )
}