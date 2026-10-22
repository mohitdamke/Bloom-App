package com.mohit.mohitbloomapp.presentation.screens.dashboard.components.meal

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.DinnerDining
import androidx.compose.material.icons.outlined.LunchDining
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mohit.mohitbloomapp.ui.theme.BloomColors
import com.mohit.mohitbloomapp.ui.theme.Fraunces


private fun mealIcon(meal: String): ImageVector = when (meal) {
    "Breakfast" -> Icons.Outlined.WbSunny
    "Lunch" -> Icons.Outlined.LunchDining
    else -> Icons.Outlined.DinnerDining
}

/** Bottom sheet opened when the user taps Meals on the Bloom dashboard. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BloomLogMealsSheet(
    selectedMeals: Map<String, String>,
    onSelectMeal: (meal: String, choice: String) -> Unit,
    onDismiss: () -> Unit
) {
    val meals = listOf("Breakfast", "Lunch", "Dinner")
    val choices = listOf("Light", "Balanced", "Indulgent", "Skipped")

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(topStart = 22.dp, topEnd = 22.dp),
        containerColor = BloomColors.Background,
        contentColor = BloomColors.Text,
        tonalElevation = 0.dp,
        dragHandle = {
            Box(
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 2.dp)
                    .size(width = 24.dp, height = 3.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE3BDB7))
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .verticalScroll(rememberScrollState())
                .padding(start = 16.dp, end = 16.dp, bottom = 24.dp)
        ) {
            // ---------- Sheet header ----------
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Log Meals",
                    modifier = Modifier.weight(1f),
                    fontSize = 22.sp,
                    fontFamily = Fraunces,
                    fontWeight = W600,
                    color = BloomColors.Text
                )
                IconButton(onClick = onDismiss) {
                    Icon(
                        Icons.Outlined.Close,
                        contentDescription = "Close",
                        tint = BloomColors.Muted
                    )
                }
            }

            Text(
                text = "How would you describe each meal today?",
                fontSize = 13.sp,
                color = BloomColors.Muted
            )

            Spacer(modifier = Modifier.height(16.dp))

            // ---------- Meals ----------
            meals.forEach { meal ->
                // Meal header (icon + title)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = mealIcon(meal),
                        contentDescription = null,

                        tint = BloomColors.Primary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = meal,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = BloomColors.Primary
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))

                // 2 x 2 grid of pill buttons
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    choices.chunked(2).forEach { rowChoices ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            rowChoices.forEach { choice ->
                                val isSelected = selectedMeals[meal] == choice
                                Surface(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clickable { onSelectMeal(meal, choice) },
                                    shape = RoundedCornerShape(50),
                                    color = if (isSelected) BloomColors.Sand else Color.White,
                                    border = BorderStroke(
                                        width = 1.5.dp,
                                        color = if (isSelected) BloomColors.Primary else BloomColors.Muted
                                    )
                                ) {
                                    Box(
                                        modifier = Modifier.padding(vertical = 13.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = choice,
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            textAlign = TextAlign.Center,
                                            color = if (isSelected) BloomColors.Primary else BloomColors.Text
                                        )
                                    }
                                }
                            }
                            if (rowChoices.size == 1) Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
                Spacer(modifier = Modifier.height(18.dp))
            }

            // ---------- Footer ----------
            Text(
                text = "${selectedMeals.size} of ${meals.size} meals logged today",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 12.sp,
                color = BloomColors.Muted
            )
        }
    }
}