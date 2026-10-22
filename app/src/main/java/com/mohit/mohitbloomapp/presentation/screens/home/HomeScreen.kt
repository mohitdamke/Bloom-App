package com.mohit.mohitbloomapp.presentation.screens.home

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.mohit.mohitbloomapp.presentation.navigation.BottomNavItem
import com.mohit.mohitbloomapp.presentation.screens.dashboard.DashboardScreen
import com.mohit.mohitbloomapp.presentation.screens.home.components.BloomBottomBar
import com.mohit.mohitbloomapp.presentation.screens.insights.InsightsScreen
import com.mohit.mohitbloomapp.presentation.screens.learn.LearnScreen
import com.mohit.mohitbloomapp.presentation.screens.settings.SettingsScreen

@Composable
fun HomeScreen() {
    var selectedRoute by rememberSaveable { mutableStateOf(BottomNavItem.Dashboard.route) }

    Scaffold(
        bottomBar = {
            BloomBottomBar(
                currentRoute = selectedRoute,
                onItemSelected = { item ->
                    selectedRoute = item.route
                }
            )
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
