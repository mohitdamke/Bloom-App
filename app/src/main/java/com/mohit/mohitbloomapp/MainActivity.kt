package com.mohit.mohitbloomapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.mohit.mohitbloomapp.navigation.AppNavigation
import com.mohit.mohitbloomapp.ui.theme.MohitBloomAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MohitBloomAppTheme {
                AppNavigation()
            }
        }
    }
}
