package com.mohit.mohitbloomapp.presentation.onboarding.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mohit.mohitbloomapp.ui.theme.BloomBackground
import com.mohit.mohitbloomapp.ui.theme.BloomBrown
import com.mohit.mohitbloomapp.ui.theme.BloomColors.SoftPink
import com.mohit.mohitbloomapp.ui.theme.BloomPink
import com.mohit.mohitbloomapp.ui.theme.BloomText
import com.mohit.mohitbloomapp.ui.theme.Fraunces
import com.mohit.mohitbloomapp.ui.theme.PlusJakartaSans

@Composable
fun AboutYouScreen(
    name: String,
    onNameChange: (String) -> Unit,
    onContinue: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BloomBackground)
            .padding(10.dp)
    ) {
        Spacer(Modifier.height(10.dp))

        // Heading at the top
        Text(
            text = "A little about you",
            modifier = Modifier.fillMaxWidth(),
            fontSize = 24.sp,
            fontFamily = Fraunces,
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.W600,
            color = BloomText,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(10.dp))

        // Input below the heading
        OutlinedTextField(
            value = name,
            onValueChange = onNameChange,
            placeholder = { Text("Your first name") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = SoftPink,
                unfocusedBorderColor = SoftPink,
                focusedTextColor = BloomText,
                unfocusedTextColor = BloomText
            )
        )

        // Push the button to the bottom
        Spacer(Modifier.weight(1f))

        Button(
            onClick = onContinue,
            enabled = name.isNotBlank(),
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = BloomBrown,
                disabledContainerColor = BloomPink
            )
        ) {
            Text(
                text = "Continue",
                color = Color.White,
                fontWeight = FontWeight.W800,
                fontFamily = PlusJakartaSans,
                fontSize = 18.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AboutYouScreenPreview() {
    AboutYouScreen(
        name = "Alex",
        onNameChange = {},
        onContinue = {}
    )
}
