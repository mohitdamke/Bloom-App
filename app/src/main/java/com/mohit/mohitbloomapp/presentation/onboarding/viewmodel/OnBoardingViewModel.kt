package com.mohit.mohitbloomapp.presentation.onboarding.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohit.mohitbloomapp.data.onboarding.OnboardingDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val dataStore: OnboardingDataStore
) : ViewModel() {

    private val _userName = MutableStateFlow("")
    val userName = _userName.asStateFlow()

    private val _selectedGoals = MutableStateFlow(
        setOf(
            "Meals",
            "Movement",
            "Water",
            "Relaxation",
            "Sleep",
            "Cycle",
            "Symptom check-in",
        )
    )
    val selectedGoals = _selectedGoals.asStateFlow()

    private val _reminderTime = MutableStateFlow("08:00 AM")
    val reminderTime = _reminderTime.asStateFlow()

    private val _reminderEnabled = MutableStateFlow(true)
    val reminderEnabled = _reminderEnabled.asStateFlow()

    private val _isCompleted = MutableStateFlow(false)
    val isCompleted = _isCompleted.asStateFlow()

    init {
        viewModelScope.launch {
            dataStore.onboardingCompleted.collect {
                _isCompleted.value = it
            }
        }
    }

    fun updateName(name: String) {
        _userName.value = name
    }

    fun toggleGoal(goal: String, enabled: Boolean) {
        _selectedGoals.value =
            if (enabled) {
                _selectedGoals.value + goal
            } else {
                _selectedGoals.value - goal
            }
    }

    fun updateReminderTime(time: String) {
        _reminderTime.value = time
    }

    fun updateReminderEnabled(enabled: Boolean) {
        _reminderEnabled.value = enabled
    }

    fun completeOnboarding(onComplete: () -> Unit) {

        if (_userName.value.isBlank()) return

        viewModelScope.launch {

            dataStore.saveOnboarding(
                name = _userName.value.trim(),
                goals = _selectedGoals.value,
                reminderTime = _reminderTime.value,
                reminderEnabled = _reminderEnabled.value
            )

            onComplete()
        }
    }

}