package com.mohit.mohitbloomapp.presentation.screens.dashboard.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mohit.mohitbloomapp.ui.theme.BloomColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BloomLogSymptomsSheet(
    selectedSymptoms: Set<String>,
    onDone: (Set<String>) -> Unit,
    onDismiss: () -> Unit
) {
    val symptoms = listOf(
        "Bloating",
        "Cramps",
        "Headache",
        "Acne / Skin changes",
        "Mood swings",
        "Fatigue",
        "Breast tenderness",
        "Back pain",
        "Sleep problems"
    )

    var currentSymptoms by remember(selectedSymptoms) {
        mutableStateOf(selectedSymptoms.toSet())
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = BloomColors.Background,
        shape = RoundedCornerShape(
            topStart = 28.dp,
            topEnd = 28.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(bottom = 24.dp)
        ) {
            Text(
                text = "Today's Symptoms",
                fontSize = 21.sp,
                color = BloomColors.Text
            )

            Spacer(Modifier.height(6.dp))

            Text(
                text = "Select any symptoms you're experiencing.",
                fontSize = 14.sp,
                color = BloomColors.Muted
            )

            Spacer(Modifier.height(12.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f, fill = false)
            ) {
                items(symptoms) { symptom ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 7.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = symptom,
                            fontSize = 15.sp,
                            color = BloomColors.Text
                        )

                        Checkbox(
                            checked = symptom in currentSymptoms,
                            onCheckedChange = { checked ->
                                currentSymptoms = if (checked) {
                                    currentSymptoms + symptom
                                } else {
                                    currentSymptoms - symptom
                                }
                            },
                            colors = CheckboxDefaults.colors(
                                checkedColor = BloomColors.Primary
                            )
                        )
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = {
                    onDone(currentSymptoms)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = BloomColors.Primary
                )
            ) {
                Text(
                    text = "Done",
                    fontSize = 16.sp
                )
            }
        }
    }
}