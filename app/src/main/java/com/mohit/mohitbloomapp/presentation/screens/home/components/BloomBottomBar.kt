package com.mohit.mohitbloomapp.presentation.screens.home.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mohit.mohitbloomapp.presentation.navigation.BottomNavItem
import com.mohit.mohitbloomapp.ui.theme.BloomPink
import com.mohit.mohitbloomapp.ui.theme.BloomText

@Composable
fun BloomBottomBar(
    currentRoute: String,
    onItemSelected: (BottomNavItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.shadow(elevation = 4.dp),
        color = Color.White,
        border = BorderStroke(
            width = 0.5.dp,
            color = BloomPink.copy(alpha = 0.5f)
        )
    ) {
        NavigationBar(
            containerColor = Color.White,
            contentColor = BloomText,
            tonalElevation = 0.dp
        ) {
            BottomNavItem.items.forEach { item ->
                val isSelected = currentRoute == item.route

                NavigationBarItem(
                    selected = isSelected,
                    onClick = { onItemSelected(item) },
                    icon = {
                        Image(
                            painter = painterResource(
                                id = if (isSelected) {
                                    item.selectedIcon
                                } else {
                                    item.unselectedIcon
                                }
                            ),
                            contentDescription = item.title,
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    label = {
                        Text(
                            text = item.title,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Normal
                        )
                    },
                    alwaysShowLabel = true,
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = Color.Transparent,
                        selectedIconColor = Color.Unspecified,
                        unselectedIconColor = Color.Unspecified,
                        selectedTextColor = BloomText,
                        unselectedTextColor = BloomText.copy(alpha = 0.65f)
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BottomPreview() {
    MaterialTheme {
        BloomBottomBar(
            currentRoute = BottomNavItem.Dashboard.route,
            onItemSelected = {}
        )
    }
}
