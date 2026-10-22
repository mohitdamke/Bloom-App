package com.mohit.mohitbloomapp.presentation.screens.settings.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mohit.mohitbloomapp.ui.theme.BloomColors

private data class JourneyStageOption(
    val title: String,
    val description: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BloomEditProfileSheet(
    userName: String,
    selectedJourneyStage: String = "General wellness",
    onSave: (String, String) -> Unit,
    onDismiss: () -> Unit
) {
    var name by remember(userName) {
        mutableStateOf(userName)
    }

    var selectedStage by remember(selectedJourneyStage) {
        mutableStateOf(selectedJourneyStage)
    }

    val journeyStages = listOf(
        JourneyStageOption(
            "Understanding my cycle",
            "Learn about my cycle, periods, and body changes."
        ),
        JourneyStageOption(
            "Trying to conceive",
            "Track fertility and prepare for pregnancy."
        ),
        JourneyStageOption(
            "Pregnant",
            "Support my wellbeing throughout pregnancy."
        ),
        JourneyStageOption(
            "Postpartum",
            "Focus on recovery and life after childbirth."
        ),
        JourneyStageOption(
            "General wellness",
            "Build healthy habits and understand my body."
        )
    )

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
                .fillMaxHeight(0.9f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
                .padding(bottom = 24.dp)
        ) {
            Text(
                text = "Edit Profile",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = BloomColors.Text
            )

            Spacer(Modifier.height(20.dp))

            Text(
                text = "Your name",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = BloomColors.Text
            )

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text("Enter your name")
                },
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = BloomColors.Primary,
                    unfocusedBorderColor = BloomColors.SoftPink,
                    focusedTextColor = BloomColors.Text,
                    unfocusedTextColor = BloomColors.Text,
                    cursorColor = BloomColors.Primary
                )
            )

            Spacer(Modifier.height(24.dp))

            Text(
                text = "Journey stage",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = BloomColors.Text
            )

            Spacer(Modifier.height(5.dp))

            Text(
                text = "Which stage best describes your journey?",
                fontSize = 13.sp,
                color = BloomColors.Muted
            )

            Spacer(Modifier.height(14.dp))

            journeyStages.forEach { stage ->
                val isSelected = selectedStage == stage.title

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                        .clickable {
                            selectedStage = stage.title
                        },
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isSelected) {
                            BloomColors.PalePink
                        } else {
                            BloomColors.Card
                        }
                    ),
                    border = if (isSelected) {
                        androidx.compose.foundation.BorderStroke(
                            1.dp,
                            BloomColors.Primary
                        )
                    } else {
                        null
                    }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = 12.dp,
                                vertical = 12.dp
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = isSelected,
                            onClick = {
                                selectedStage = stage.title
                            },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = BloomColors.Primary,
                                unselectedColor = BloomColors.Muted
                            )
                        )

                        Spacer(Modifier.width(8.dp))

                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = stage.title,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = BloomColors.Text
                            )

                            Spacer(Modifier.height(4.dp))

                            Text(
                                text = stage.description,
                                fontSize = 12.sp,
                                lineHeight = 17.sp,
                                color = BloomColors.Muted
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(12.dp))

            Button(
                onClick = {
                    if (name.isNotBlank()) {
                        onSave(name.trim(), selectedStage)
                    }
                },
                enabled = name.isNotBlank(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = BloomColors.Primary
                )
            ) {
                Text(
                    text = "Save Changes",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(Modifier.height(12.dp))
        }
    }
}
