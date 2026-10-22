package com.mohit.mohitbloomapp.presentation.screens.dashboard.components

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
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val MealSheetBackground = Color(0xFFFCF9F7)
private val MealSheetPrimary = Color(0xFFAD5750)
private val MealSheetText = Color(0xFF332A27)
private val MealSheetMuted = Color(0xFF927F76)
private val MealSheetBorder = Color(0xFFF2E4E0)
private val MealSheetSelected = Color(0xFFFAF0EE)

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
        containerColor = MealSheetBackground,
        contentColor = MealSheetText,
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Log Meals",
                    modifier = Modifier.weight(1f),
                    color = MealSheetText,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontStyle = FontStyle.Italic
                )
                IconButton(onClick = onDismiss) {
                    Icon(Icons.Outlined.Close, contentDescription = "Close", tint = MealSheetMuted)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            meals.forEach { meal ->
                Text(
                    text = meal,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = MealSheetText
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    choices.forEach { choice ->
                        val isSelected = selectedMeals[meal] == choice
                        Surface(
                            modifier = Modifier
                                .weight(1f)
                                .clickable { onSelectMeal(meal, choice) },
                            shape = RoundedCornerShape(14.dp),
                            color = if (isSelected) MealSheetSelected else Color.White,
                            border = BorderStroke(
                                width = 1.dp,
                                color = if (isSelected) MealSheetPrimary else MealSheetBorder
                            )
                        ) {
                            Box(
                                modifier = Modifier.padding(vertical = 10.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = choice,
                                    fontSize = 12.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    color = if (isSelected) MealSheetPrimary else MealSheetText
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
