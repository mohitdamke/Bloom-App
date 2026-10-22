package com.mohit.mohitbloomapp.presentation.screens.home

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.mohit.mohitbloomapp.ads.AdaptiveBannerAd
import com.mohit.mohitbloomapp.internet.isInternetAvailable
import com.mohit.mohitbloomapp.presentation.navigation.BottomNavItem
import com.mohit.mohitbloomapp.presentation.screens.dashboard.DashboardScreen
import com.mohit.mohitbloomapp.presentation.screens.home.components.BloomBottomBar
import com.mohit.mohitbloomapp.presentation.screens.insights.InsightsScreen
import com.mohit.mohitbloomapp.presentation.screens.learn.LearnScreen
import com.mohit.mohitbloomapp.presentation.screens.settings.SettingsScreen
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun HomeScreen() {
    var selectedRoute by rememberSaveable {
        mutableStateOf(BottomNavItem.Dashboard.route)
    }

    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        var wasOffline = false

        while (true) {
            val isOnline = isInternetAvailable(context)

            if (!isOnline && !wasOffline) {
                snackbarHostState.showSnackbar(
                    message = "No internet connection"
                )
            }

            wasOffline = !isOnline
            delay(5000.milliseconds)
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        bottomBar = {
            Column {
                AdaptiveBannerAd()

                BloomBottomBar(
                    currentRoute = selectedRoute,
                    onItemSelected = { item ->
                        selectedRoute = item.route
                    }
                )
            }
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Crossfade(
                targetState = selectedRoute,
                animationSpec = tween(durationMillis = 300),
                label = "BottomNavCrossfade"
            ) { route ->
                when (route) {
                    BottomNavItem.Dashboard.route -> DashboardScreen()
                    BottomNavItem.Insights.route -> InsightsScreen()
                    BottomNavItem.Learn.route -> LearnScreen()
                    BottomNavItem.Settings.route -> SettingsScreen()
                    else -> DashboardScreen()
                }
            }
        }
    }
}