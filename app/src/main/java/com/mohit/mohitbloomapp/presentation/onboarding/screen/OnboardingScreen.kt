package com.mohit.mohitbloomapp.presentation.onboarding.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import com.mohit.mohitbloomapp.R
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.mohit.mohitbloomapp.presentation.onboarding.viewmodel.OnboardingViewModel
import com.mohit.mohitbloomapp.ui.theme.BloomBackground
import com.mohit.mohitbloomapp.ui.theme.BloomBrown
import com.mohit.mohitbloomapp.ui.theme.BloomPink

@Composable
fun OnboardingScreen(
    onComplete: () -> Unit
) {
    val viewModel: OnboardingViewModel = hiltViewModel()

    var step by remember { mutableIntStateOf(0) }

    val name by viewModel.userName.collectAsState()
    val goals by viewModel.selectedGoals.collectAsState()
    val reminderTime by viewModel.reminderTime.collectAsState()
    val reminderEnabled by viewModel.reminderEnabled.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BloomBackground)
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .imePadding()
            .padding(horizontal = 20.dp)
    ) {
        Spacer(Modifier.height(24.dp))

// Fixed top row: back button + progress lines
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (step > 0) {
                Image(
                    painter = painterResource(id = R.drawable.button),
                    contentDescription = "Back",
                    modifier = Modifier
                        .size(32.dp)
                        .clickable { step-- }
                )
                Spacer(Modifier.width(8.dp))
            } else {
                Spacer(Modifier.width(40.dp))
            }

            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                repeat(4) { index ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(5.dp)
                            .background(
                                color = if (index <= step) BloomBrown else BloomPink,
                                shape = RoundedCornerShape(4.dp)
                            )
                    )
                }
            }

            Spacer(Modifier.width(40.dp))
        }

        Spacer(Modifier.height(12.dp))

        when (step) {
            0 -> WelcomeScreen(
                onContinue = { step = 1 }
            )

            1 -> AboutYouScreen(
                name = name,
                onNameChange = viewModel::updateName,
                onContinue = { step = 2 }
            )

            2 -> GoalsScreen(
                selectedGoals = goals,
                onGoalToggle = viewModel::toggleGoal,
                onContinue = { step = 3 }
            )

            3 -> ReminderScreen(
                reminderTime = reminderTime,
                reminderEnabled = reminderEnabled,
                onReminderTimeChange = viewModel::updateReminderTime,
                onReminderEnabledChange = viewModel::updateReminderEnabled,
                onComplete = {
                    viewModel.completeOnboarding {
                        onComplete()
                    }
                }
            )
        }
    }
}