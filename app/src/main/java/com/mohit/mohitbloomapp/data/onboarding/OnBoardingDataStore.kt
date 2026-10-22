package com.mohit.mohitbloomapp.data.onboarding

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(
    name = "bloom_preferences"
)

@Singleton
class OnboardingDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {

    companion object {
        val ONBOARDING_COMPLETED =
            booleanPreferencesKey("onboarding_completed")

        val USER_NAME =
            stringPreferencesKey("user_name")

        val SELECTED_GOALS =
            stringPreferencesKey("selected_goals")

        val REMINDER_TIME =
            stringPreferencesKey("reminder_time")

        val REMINDER_ENABLED =
            booleanPreferencesKey("reminder_enabled")
    }

    val onboardingCompleted: Flow<Boolean> =
        context.dataStore.data.map { preferences ->
            preferences[ONBOARDING_COMPLETED] ?: false
        }

    suspend fun saveOnboarding(
        name: String,
        goals: Set<String>,
        reminderTime: String,
        reminderEnabled: Boolean
    ) {
        context.dataStore.edit { preferences ->
            preferences[USER_NAME] = name
            preferences[SELECTED_GOALS] = goals.joinToString(",")
            preferences[REMINDER_TIME] = reminderTime
            preferences[REMINDER_ENABLED] = reminderEnabled

            // Mark onboarding complete only when all details are saved.
            preferences[ONBOARDING_COMPLETED] = true
        }
    }

    val userName: Flow<String> =
        context.dataStore.data.map { preferences ->
            preferences[USER_NAME] ?: ""
        }

    val selectedGoals: Flow<Set<String>> =
        context.dataStore.data.map { preferences ->
            preferences[SELECTED_GOALS]
                ?.split(",")
                ?.filter { it.isNotBlank() }
                ?.toSet()
                ?: emptySet()
        }

    val reminderTime: Flow<String> =
        context.dataStore.data.map { preferences ->
            preferences[REMINDER_TIME] ?: "08:00 AM"
        }

    val reminderEnabled: Flow<Boolean> =
        context.dataStore.data.map { preferences ->
            preferences[REMINDER_ENABLED] ?: true
        }
}
