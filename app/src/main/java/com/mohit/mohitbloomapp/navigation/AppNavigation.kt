package com.mohit.mohitbloomapp.navigation

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mohit.mohitbloomapp.ads.AppOpenAdManager
import com.mohit.mohitbloomapp.presentation.onboarding.screen.OnboardingScreen
import com.mohit.mohitbloomapp.presentation.screens.SplashScreen
import com.mohit.mohitbloomapp.presentation.screens.home.HomeScreen
import com.mohit.mohitbloomapp.presentation.splash.SplashState
import com.mohit.mohitbloomapp.presentation.splash.SplashViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.Splash
    ) {
        composable(Routes.Splash) {
            val viewModel: SplashViewModel = hiltViewModel()
            val state by viewModel.state.collectAsStateWithLifecycle()
            val context = LocalContext.current

            LaunchedEffect(viewModel) {
                viewModel.start()
            }

            LaunchedEffect(state) {
                when (state) {
                    SplashState.Loading -> Unit

                    SplashState.Onboarding -> {
                        navController.navigate(Routes.Onboarding) {
                            popUpTo(Routes.Splash) { inclusive = true }
                            launchSingleTop = true
                        }
                    }

                    SplashState.ShowAppOpenAd -> {
                        val activity = context.findActivity()

                        if (activity == null) {
                            viewModel.onAdCouldNotStart()
                        } else {
                            val started = AppOpenAdManager.showIfAvailable(activity) {
                                viewModel.onAdFlowFinished()
                            }

                            // If an ad is already showing, don't navigate.
                            // Its existing callback will update the ViewModel.
                            if (!started && !AppOpenAdManager.isAdShowing()) {
                                viewModel.onAdCouldNotStart()
                            }
                        }
                    }

                    SplashState.Home -> {
                        navController.navigate(Routes.Home) {
                            popUpTo(Routes.Splash) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                }
            }

            SplashScreen()
        }

        composable(Routes.Onboarding) {
            OnboardingScreen(
                onComplete = {
                    AppOpenAdManager.enableAds()

                    navController.navigate(Routes.Home) {
                        popUpTo(Routes.Onboarding) { inclusive = true }
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

private fun Context.findActivity(): Activity? {
    var current: Context = this

    while (current is ContextWrapper) {
        if (current is Activity) return current
        current = current.baseContext
    }

    return null
}