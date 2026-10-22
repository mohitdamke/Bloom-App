package com.mohit.mohitbloomapp.navigation

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mohit.mohitbloomapp.ads.AppOpenAdManager
import com.mohit.mohitbloomapp.data.onboarding.OnboardingDataStore
import com.mohit.mohitbloomapp.presentation.onboarding.screen.OnboardingScreen
import com.mohit.mohitbloomapp.presentation.screens.SplashScreen
import com.mohit.mohitbloomapp.presentation.screens.home.HomeScreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun AppNavigation() {

    val context = LocalContext.current
    val navController = rememberNavController()

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

                if (!onboardingCompleted) {

                    // First-time users should not see an App Open Ad.
                    navController.navigate(Routes.Onboarding) {
                        popUpTo(Routes.Splash) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }

                } else {

                    // Returning users can see App Open Ads.
                    AppOpenAdManager.enableAds()

                    val activity = context.findActivity()

                    val adStarted = activity?.let {
                        AppOpenAdManager.waitAndShowAd(it) {
                            navigateToHome(navController)
                        }
                    } ?: false

                    // Navigate immediately if no ad could be shown.
                    if (!adStarted) {
                        navigateToHome(navController)
                    }
                }
            }
        }

        composable(Routes.Onboarding) {

            OnboardingScreen(
                onComplete = {

                    // Enable ads for future app openings.
                    // Do not show an ad immediately after onboarding.
                    AppOpenAdManager.enableAds()

                    navController.navigate(Routes.Home) {
                        popUpTo(Routes.Onboarding) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(Routes.Home) {
            HomeScreen()
        }
    }
}

private fun navigateToHome(
    navController: NavHostController
) {
    navController.navigate(Routes.Home) {
        popUpTo(Routes.Splash) {
            inclusive = true
        }
        launchSingleTop = true
    }
}

private fun Context.findActivity(): Activity? {
    var currentContext = this

    while (currentContext is ContextWrapper) {
        if (currentContext is Activity) {
            return currentContext
        }

        currentContext = currentContext.baseContext
    }

    return null
}