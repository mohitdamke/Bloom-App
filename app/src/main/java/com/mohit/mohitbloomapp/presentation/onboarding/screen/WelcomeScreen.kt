package com.mohit.mohitbloomapp.presentation.onboarding.screen


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mohit.mohitbloomapp.R
import com.mohit.mohitbloomapp.ui.theme.BloomBackground
import com.mohit.mohitbloomapp.ui.theme.BloomBrown
import com.mohit.mohitbloomapp.ui.theme.BloomColors.welcomeText
import com.mohit.mohitbloomapp.ui.theme.BloomText
import com.mohit.mohitbloomapp.ui.theme.Fraunces
import com.mohit.mohitbloomapp.ui.theme.PlusJakartaSans

@Composable
fun WelcomeScreen(
    onContinue: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BloomBackground)
    ) {
        // Background gradient
        Image(
            painter = painterResource(R.drawable.gradient),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .align(Alignment.Center)
                .size(width = 300.dp, height = 400.dp)
        )

        // Decorative stars
        Image(
            painter = painterResource(R.drawable.container),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 65.dp, top = 100.dp)
                .size(22.dp)
        )

        Image(
            painter = painterResource(R.drawable.container),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(end = 65.dp, top = 120.dp)
                .size(18.dp)
        )

        Image(
            painter = painterResource(R.drawable.container),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 75.dp, bottom = 150.dp)
                .size(22.dp)
        )

        // Main content: icon and text centered
        Column(
        modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.Center)
            .offset(y = (-20).dp)
            .padding(horizontal = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(R.drawable.welcomeicon),
                contentDescription = null,
                modifier = Modifier.size(120.dp)
            )

            Spacer(Modifier.height(12.dp))

            Text(
                text = "Welcome to Bloom",
                fontFamily = Fraunces,
                fontWeight = FontWeight.SemiBold,
                fontStyle = FontStyle.Italic,
                fontSize = 30.sp,
                color = BloomText,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(12.dp))

            Text(
                text = "A simple daily companion for managing PCOS — track your cycle, meals, movement, water, relaxation and sleep.",
                fontFamily = PlusJakartaSans,
                fontWeight = FontWeight.W400,
                fontSize = 14.sp,
                color = welcomeText,
                textAlign = TextAlign.Center
            )
        }

        // Get started button at the bottom
        Button(
            onClick = onContinue,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(horizontal = 10.dp)
                .padding(bottom = 24.dp)
                .height(52.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = BloomBrown
            )
        ) {
            Text(
                text = "Get started",
                color = Color.White,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = PlusJakartaSans,
                fontSize = 18.sp
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun WelcomeScreenPreview() {
    WelcomeScreen(onContinue = {})
}
