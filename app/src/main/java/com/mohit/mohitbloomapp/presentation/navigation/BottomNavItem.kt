package com.mohit.mohitbloomapp.presentation.navigation

import androidx.annotation.DrawableRes
import com.mohit.mohitbloomapp.R

sealed class BottomNavItem(
    val route: String,
    val title: String,
    @DrawableRes val selectedIcon: Int,
    @DrawableRes val unselectedIcon: Int
) {
    data object Dashboard : BottomNavItem(
        route = "dashboard",
        title = "Dashboard",
        selectedIcon = R.drawable.clickhome,
        unselectedIcon = R.drawable.home
    )

    data object Insights : BottomNavItem(
        route = "insights",
        title = "Insights",
        selectedIcon = R.drawable.clickinsights,
        unselectedIcon = R.drawable.insights
    )

    data object Learn : BottomNavItem(
        route = "learn",
        title = "Learn",
        selectedIcon = R.drawable.clicklearn,
        unselectedIcon = R.drawable.learn
    )

    data object Settings : BottomNavItem(
        route = "settings",
        title = "Settings",
        selectedIcon = R.drawable.clicksetting,
        unselectedIcon = R.drawable.setting
    )

    companion object {
        val items: List<BottomNavItem>
            get() = listOf(
                Dashboard,
                Insights,
                Learn,
                Settings
            )
    }
}
