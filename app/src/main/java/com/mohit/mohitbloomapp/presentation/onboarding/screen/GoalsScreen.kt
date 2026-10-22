package com.mohit.mohitbloomapp.presentation.onboarding.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mohit.mohitbloomapp.R
import com.mohit.mohitbloomapp.ui.theme.BloomBackground
import com.mohit.mohitbloomapp.ui.theme.BloomBrown
import com.mohit.mohitbloomapp.ui.theme.BloomColors.welcomeText
import com.mohit.mohitbloomapp.ui.theme.BloomPink
import com.mohit.mohitbloomapp.ui.theme.BloomText
import com.mohit.mohitbloomapp.ui.theme.Fraunces
import com.mohit.mohitbloomapp.ui.theme.PlusJakartaSans

private data class GoalItem(
    val title: String,
    val subtitle: String,
    val description: String,
    val icon: Int
)

private val goalItems = listOf(
    GoalItem(
        "Meals",
        "3 balanced meals",
        "Steadier blood sugar helps keep PCOS symptoms in check.",
        R.drawable.goalmeal
    ),
    GoalItem(
        "Movement",
        "30 minutes",
        "Supports insulin sensitivity, a key lever in PCOS.",
        R.drawable.goalmovement
    ),
    GoalItem(
        "Water",
        "8 glasses",
        "Helps with energy and bloating.",
        R.drawable.goalwater
    ),
    GoalItem(
        "Relaxation",
        "15 minutes",
        "Lower stress means lower cortisol, which can worsen symptoms.",
        R.drawable.goalrelax
    ),
    GoalItem(
        "Sleep",
        "8 hours",
        "Consistent sleep helps balance hormones over time.",
        R.drawable.goalsleep
    ),
    GoalItem(
        "Cycle",
        "period dates",
        "Spot your personal pattern and predict your next period.",
        R.drawable.goalcycle
    ),
    GoalItem(
        "Symptom check-in",
        "bloating, skin, mood, sleep quality",
        "Track how you feel day to day to notice patterns over time.",
        R.drawable.goalsympton
    )
)

@Composable
fun GoalsScreen(
    selectedGoals: Set<String>,
    onGoalToggle: (String, Boolean) -> Unit,
    onContinue: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BloomBackground)
            .padding(horizontal = 10.dp, vertical = 8.dp)
    ) {
        Spacer(Modifier.height(4.dp))

        Image(
            painter = painterResource(R.drawable.goallogo),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(40.dp)
        )

        Spacer(Modifier.height(12.dp))

        Text(
            text = "What would you like to track daily?",
            modifier = Modifier.fillMaxWidth(),
            fontSize = 24.sp,
            fontFamily = Fraunces,
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.SemiBold,
            color = BloomText,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(10.dp))

        Text(
            text = "Pick the goals that matter to you — only these will " +
                    "show up on your dashboard. Partial progress still " +
                    "counts, and your flower fills in as you go.",
            modifier = Modifier.fillMaxWidth(),
            fontFamily = PlusJakartaSans,
            fontSize = 14.sp,
            color = welcomeText,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(24.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(30.dp)
                )
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
                goalItems.forEachIndexed { index, goal ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        // Goal icon
                        Box(
                            modifier = Modifier
                                .padding(top = 2.dp)
                                .size(42.dp)
                                .background(
                                    color = BloomPink.copy(alpha = 0.25f),
                                    shape = RoundedCornerShape(14.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(goal.icon),
                                contentDescription = goal.title,
                                contentScale = ContentScale.Fit,
                                modifier = Modifier.size(22.dp)
                            )
                        }

                        Spacer(Modifier.width(12.dp))

                        // Title and description
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = "${goal.title} · ${goal.subtitle}",
                                fontSize = 14.sp,
                                fontFamily = PlusJakartaSans,
                                fontWeight = FontWeight.Bold,
                                color = BloomText,
                                lineHeight = 19.sp
                            )

                            Spacer(Modifier.height(4.dp))

                            Text(
                                text = goal.description,
                                fontSize = 12.sp,
                                fontFamily = PlusJakartaSans,
                                color = welcomeText,
                                lineHeight = 17.sp
                            )
                        }

                        Spacer(Modifier.width(6.dp))

                        // Switch aligned with the top of the text
                        Switch(
                            checked = goal.title in selectedGoals,
                            onCheckedChange = {
                                onGoalToggle(goal.title, it)
                            },
                            modifier = Modifier.padding(top = 0.dp),
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = BloomBrown,
                                checkedBorderColor = BloomBrown,
                                uncheckedThumbColor = Color.White,
                                uncheckedTrackColor = BloomPink,
                                uncheckedBorderColor = BloomPink
                            )
                        )
                    }

                    if (index != goalItems.lastIndex) {
                        HorizontalDivider(
                            color = BloomPink.copy(alpha = 0.35f),
                            thickness = 1.dp
                        )
                    }
                }
            }
        }


        Spacer(Modifier.height(12.dp))

        Text(
            text = "Targets and what you track can be adjusted any time in Settings.",
            modifier = Modifier.fillMaxWidth(),
            fontFamily = PlusJakartaSans,
            fontSize = 12.sp,
            color = welcomeText,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(12.dp))

        Button(
            onClick = onContinue,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = BloomBrown
            )
        ) {
            Text(
                text = "Continue",
                color = Color.White,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = PlusJakartaSans,
                fontSize = 18.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun GoalsScreenPreview() {
    GoalsScreen(
        selectedGoals = setOf("Meals", "Water", "Sleep"),
        onGoalToggle = { _, _ -> },
        onContinue = {}
    )
}
