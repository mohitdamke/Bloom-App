package com.mohit.mohitbloomapp.presentation.screens.dashboard.components.symptons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.Bedtime
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Mood
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mohit.mohitbloomapp.presentation.screens.dashboard.components.allinone.BloomRoundButton
import com.mohit.mohitbloomapp.presentation.screens.dashboard.components.allinone.BloomText
import com.mohit.mohitbloomapp.presentation.screens.dashboard.components.allinone.BloomTextStyle
import com.mohit.mohitbloomapp.presentation.screens.dashboard.components.allinone.BloomUi
import com.mohit.mohitbloomapp.ui.theme.Fraunces

/** One symptom category and the choices the user can pick from. */
data class SymptomCategory(
    val name: String,
    val icon: ImageVector,
    val options: List<String>
)

private val defaultSymptomCategories = listOf(
    SymptomCategory("Bloating", Icons.Outlined.WaterDrop, listOf("None", "Mild", "Moderate", "Severe")),
    SymptomCategory("Skin & acne", Icons.Outlined.AutoAwesome, listOf("Clear", "Mild", "Moderate", "Flare-up")),
    SymptomCategory("Mood", Icons.Outlined.Mood, listOf("Great", "Okay", "Low", "Very low")),
    SymptomCategory("Sleep quality", Icons.Outlined.Bedtime, listOf("Great", "Okay", "Poor", "Very poor"))
)

/**
 * Bottom sheet for logging today's symptoms.
 *
 * [selectedSymptoms] maps a category name to the chosen option,
 * e.g. mapOf("Bloating" to "Mild", "Mood" to "Okay").
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BloomLogSymptomsSheet(
    selectedSymptoms: Map<String, String>,
    onDone: (Map<String, String>) -> Unit,
    onDismiss: () -> Unit,
    categories: List<SymptomCategory> = defaultSymptomCategories
) {
    var current by remember(selectedSymptoms) { mutableStateOf(selectedSymptoms) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        containerColor = BloomUi.Background,
        shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(horizontal = 20.dp)
                .padding(bottom = 16.dp)
        ) {
            // ---------- Header ----------
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Today's symptoms",
                    modifier = Modifier.weight(1f),
                    fontSize = 26.sp,
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

            Spacer(Modifier.height(10.dp))

            BloomText(
                text = "These are separate from your daily goals — logging them just helps you (and eventually your doctor) spot patterns over time.",
                style = BloomTextStyle.Caption,
                size = 14.sp
            )

            Spacer(Modifier.height(16.dp))

            // ---------- Categories ----------
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f, fill = false),
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                items(categories, key = { it.name }) { category ->
                    BloomCategoryHeader(icon = category.icon, title = category.name)
                    Spacer(Modifier.height(10.dp))
                    BloomChoiceRow(
                        options = category.options,
                        selected = current[category.name],
                        onSelect = { option ->
                            // tap again to unselect
                            current = if (current[category.name] == option) {
                                current - category.name
                            } else {
                                current + (category.name to option)
                            }
                        }
                    )
                }
            }

            Spacer(Modifier.height(20.dp))

            BloomPrimaryButton(text = "Done", onClick = { onDone(current) })
        }
    }
}

/* ------------------------------------------------------------------ */
/*  Reusable pieces (move these to BloomComponents.kt if you like)     */
/* ------------------------------------------------------------------ */

/** Small icon + bold title, e.g. "Bloating". */
@Composable
fun BloomCategoryHeader(
    icon: ImageVector,
    title: String,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = BloomUi.Primary,
            modifier = Modifier.size(16.dp)
        )
        Spacer(Modifier.width(8.dp))
        BloomText(
            text = title,
            style = BloomTextStyle.Heading,
            color = BloomUi.Primary,
            size = 16.sp
        )
    }
}

/** A single rounded option pill (selected / unselected). */
@Composable
fun BloomChoiceChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(50),
        color = if (selected) BloomUi.CardPink else Color.White,
        border = BorderStroke(1.5.dp, if (selected) BloomUi.Primary else BloomUi.Border)
    ) {
        Box(
            modifier = Modifier.padding(vertical = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            BloomText(
                text = text,
                style = BloomTextStyle.Button,
                color = if (selected) BloomUi.Primary else BloomUi.TextDark,
                size = 13.sp,
                weight = FontWeight.SemiBold,
                align = TextAlign.Center
            )
        }
    }
}

/** A row of equal-width [BloomChoiceChip]s. */
@Composable
fun BloomChoiceRow(
    options: List<String>,
    selected: String?,
    onSelect: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        options.forEach { option ->
            BloomChoiceChip(
                text = option,
                selected = option == selected,
                onClick = { onSelect(option) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

/** Large full-width rounded action button (the "Done" button). */
@Composable
fun BloomPrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(
            containerColor = BloomUi.Primary,
            contentColor = Color.White
        )
    ) {
        Text(
            text = text,
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold
        )
    }
}