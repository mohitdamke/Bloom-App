package com.mohit.mohitbloomapp.presentation.onboarding.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohit.mohitbloomapp.data.onboarding.OnboardingDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val dataStore: OnboardingDataStore
) : ViewModel() {

    private val totalPages = 4

    private val _currentPage = MutableStateFlow(0)
    val currentPage = _currentPage.asStateFlow()

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
            "Symptom check-in"
        )
    )
    val selectedGoals = _selectedGoals.asStateFlow()

    private val _reminderTime = MutableStateFlow("08:00 AM")
    val reminderTime = _reminderTime.asStateFlow()

    private val _reminderEnabled = MutableStateFlow(true)
    val reminderEnabled = _reminderEnabled.asStateFlow()

    private val _isCompleted = MutableStateFlow(false)
    val isCompleted = _isCompleted.asStateFlow()

    private val _isSaving = MutableStateFlow(false)
    val isSaving = _isSaving.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    val isFirstPage: Boolean
        get() = _currentPage.value == 0

    val isLastPage: Boolean
        get() = _currentPage.value == totalPages - 1

    fun nextPage() {
        if (_currentPage.value < totalPages - 1) {
            if (_currentPage.value == 1 && _userName.value.isBlank()) {
                _errorMessage.value = "Please enter your name"
                return
            }

            _errorMessage.value = null
            _currentPage.value++
        }
    }

    fun previousPage() {
        if (_currentPage.value > 0) {
            _errorMessage.value = null
            _currentPage.value--
        }
    }

    fun updateName(name: String) {
        _userName.value = name
        if (name.isNotBlank()) {
            _errorMessage.value = null
        }
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

    fun completeOnboarding() {
        if (_userName.value.isBlank()) {
            _errorMessage.value = "Please enter your name"
            _currentPage.value = 1
            return
        }

        if (_isSaving.value || _isCompleted.value) return

        viewModelScope.launch {
            _isSaving.value = true
            _errorMessage.value = null

            try {
                dataStore.saveOnboarding(
                    name = _userName.value.trim(),
                    goals = _selectedGoals.value,
                    reminderTime = _reminderTime.value,
                    reminderEnabled = _reminderEnabled.value
                )

                // saveOnboarding must persist onboardingCompleted = true.
                _isCompleted.value = true
            } catch (exception: Exception) {
                _errorMessage.value =
                    "Unable to save your details. Please try again."
            } finally {
                _isSaving.value = false
            }
        }
    }
}
