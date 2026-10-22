package com.mohit.mohitbloomapp.presentation.onboarding.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mohit.mohitbloomapp.R
import com.mohit.mohitbloomapp.presentation.onboarding.viewmodel.OnboardingViewModel
import com.mohit.mohitbloomapp.ui.theme.BloomBackground
import com.mohit.mohitbloomapp.ui.theme.BloomBrown
import com.mohit.mohitbloomapp.ui.theme.BloomPink

@Composable
fun OnboardingScreen(
    onComplete: () -> Unit,
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    val currentPage by viewModel.currentPage.collectAsStateWithLifecycle()
    val name by viewModel.userName.collectAsStateWithLifecycle()
    val goals by viewModel.selectedGoals.collectAsStateWithLifecycle()
    val reminderTime by viewModel.reminderTime.collectAsStateWithLifecycle()
    val reminderEnabled by viewModel.reminderEnabled.collectAsStateWithLifecycle()
    val isCompleted by viewModel.isCompleted.collectAsStateWithLifecycle()
    val isSaving by viewModel.isSaving.collectAsStateWithLifecycle()
    val errorMessage by viewModel.errorMessage.collectAsStateWithLifecycle()

    LaunchedEffect(isCompleted) {
        if (isCompleted) {
            onComplete()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BloomBackground)
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .imePadding()
            .padding(horizontal = 20.dp)
    ) {
        Spacer(Modifier.height(24.dp))

        // Back button and progress indicators
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (currentPage > 0) {
                Image(
                    painter = painterResource(id = R.drawable.button),
                    contentDescription = "Back",
                    modifier = Modifier
                        .size(32.dp)
                        .clickable {
                            viewModel.previousPage()
                        }
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
                                color = if (index <= currentPage) {
                                    BloomBrown
                                } else {
                                    BloomPink
                                },
                                shape = RoundedCornerShape(4.dp)
                            )
                    )
                }
            }

            Spacer(Modifier.width(40.dp))
        }

        Spacer(Modifier.height(12.dp))

        when (currentPage) {
            0 -> WelcomeScreen(
                onContinue = viewModel::nextPage
            )

            1 -> AboutYouScreen(
                name = name,
                onNameChange = viewModel::updateName,
                onContinue = viewModel::nextPage
            )

            2 -> GoalsScreen(
                selectedGoals = goals,
                onGoalToggle = viewModel::toggleGoal,
                onContinue = viewModel::nextPage
            )

            3 -> ReminderScreen(
                reminderTime = reminderTime,
                reminderEnabled = reminderEnabled,
                onReminderTimeChange = viewModel::updateReminderTime,
                onReminderEnabledChange = viewModel::updateReminderEnabled,
                onComplete = viewModel::completeOnboarding
            )
        }

        errorMessage?.let { message ->
            Spacer(Modifier.height(8.dp))

            Text(
                text = message,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        if (isSaving) {
            Spacer(Modifier.height(8.dp))

            CircularProgressIndicator()
        }
    }
}