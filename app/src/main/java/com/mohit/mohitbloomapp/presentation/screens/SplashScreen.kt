package com.mohit.mohitbloomapp.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.mohit.mohitbloomapp.R

@Composable
fun SplashScreen(modifier: Modifier = Modifier) {


    Image(
        painter = painterResource(id = R.drawable.splash),
        contentDescription = null,
        modifier = modifier.fillMaxSize()
    )


}

@Preview(showBackground = true)
@Composable
private fun SplashScreenPreview() {
    SplashScreen()
}