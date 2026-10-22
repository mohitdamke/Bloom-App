package com.mohit.mohitbloomapp.navigation

import android.app.Application
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mohit.mohitbloomapp.data.onboarding.OnboardingDataStore
import com.mohit.mohitbloomapp.presentation.onboarding.screen.OnboardingScreen
import com.mohit.mohitbloomapp.presentation.onboarding.viewmodel.OnboardingViewModel
import com.mohit.mohitbloomapp.presentation.screens.HomeScreen
import com.mohit.mohitbloomapp.presentation.screens.SplashScreen
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun AppNavigation() {

    val context = LocalContext.current
    val navController = rememberNavController()
    val onboardingViewModel: OnboardingViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = Routes.Splash
    ) {

        composable(Routes.Splash) {

            SplashScreen()

            LaunchedEffect(Unit) {

                delay(1500.milliseconds)

                val dataStore = OnboardingDataStore(context)

                val onboardingCompleted =
                    dataStore.onboardingCompleted.first()

                val destination = if (onboardingCompleted) {
                    Routes.Home
                } else {
                    Routes.Onboarding
                }

                navController.navigate(destination) {
                    popUpTo(Routes.Splash) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }
            }
        }

        composable(Routes.Onboarding) {


            OnboardingScreen(
                viewModel = onboardingViewModel,
                onComplete = {
                    navController.navigate(Routes.Home) {
                        popUpTo(Routes.Onboarding) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }}
            )
        }

        composable(Routes.Home) {
            HomeScreen()
        }
    }
}
