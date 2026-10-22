package com.mohit.mohitbloomapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mohit.mohitbloomapp.data.onboarding.OnboardingDataStore
import com.mohit.mohitbloomapp.presentation.onboarding.screen.OnboardingScreen
import com.mohit.mohitbloomapp.presentation.screens.HomeScreen
import com.mohit.mohitbloomapp.presentation.screens.SplashScreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun AppNavigation() {

    val context = LocalContext.current
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.Home
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
