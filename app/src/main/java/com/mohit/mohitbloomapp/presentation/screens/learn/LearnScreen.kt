package com.mohit.mohitbloomapp.presentation.screens.learn

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mohit.mohitbloomapp.ui.theme.BloomColors
import com.mohit.mohitbloomapp.ui.theme.Fraunces
import com.mohit.mohitbloomapp.ui.theme.PlusJakartaSans

@Composable
fun LearnScreen() {
    BloomLearnScreen()
}

@Composable
fun BloomLearnScreen(onQuestionClick: (String) -> Unit = {}) {
    val groups = listOf(
        "Diagnosis & basics" to listOf(
            "What exactly is PCOS?",
            "What does PCOS stand for — and do I actually have cysts on my ovaries?",
            "What causes PCOS?",
            "How is PCOS actually diagnosed?",
            "Is PCOS the same for everyone?"
        ),
        "Symptoms & body" to listOf(
            "Why are my periods irregular?",
            "Why does weight feel harder to manage?",
            "What is the deal with insulin resistance?",
            "Is the acne and hair growth from PCOS treatable?"
        ),
        "Nutrition & lifestyle" to listOf(
            "What diet works best for PCOS?",
            "Should I avoid all carbs and sugar?",
            "What exercise is best for PCOS?",
            "Does stress affect PCOS symptoms?"
        ),
        "Treatment & fertility" to listOf(
            "Can PCOS be cured?",
            "What medications are used for PCOS?",
            "Can I get pregnant if I have PCOS?",
            "When should I see a doctor or specialist?"
        )
    )

    var expandedQuestion by rememberSaveable { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BloomColors.Background)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 18.dp)
            .padding(top = 26.dp, bottom = 24.dp)
    ) {
        // Screen Header
        Text(
            text = "Learn",
            fontSize = 28.sp,
            fontWeight = FontWeight.W800,
            fontFamily = Fraunces,
            color = BloomColors.Text
        )
        Text(
            text = "Understand PCOS, at your own pace",
            fontSize = 12.sp,
            fontWeight = FontWeight.W400,
            fontFamily = PlusJakartaSans,
            color = BloomColors.Muted
        )

        Spacer(modifier = Modifier.height(10.dp))

        // FAQ Category Groups
        groups.forEach { (categoryTitle, questions) ->
            BloomSectionTitle(
                title = categoryTitle,
            )

            Spacer(modifier = Modifier.height(10.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                questions.forEach { question ->
                    val isExpanded = expandedQuestion == question
                    QuestionCard(
                        question = question,
                        isExpanded = isExpanded,
                        onClick = {
                            expandedQuestion = if (isExpanded) null else question
                            onQuestionClick(question)
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
private fun QuestionCard(
    question: String,
    isExpanded: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = BloomColors.Card),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = question,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W700,
                    fontFamily = PlusJakartaSans,
                    color = BloomColors.Text,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Icon(
                    imageVector = if (isExpanded) Icons.Outlined.ExpandMore else Icons.Outlined.ChevronRight,
                    contentDescription = null,
                    tint = BloomColors.Primary,
                    modifier = Modifier.size(20.dp)
                )
            }

            AnimatedVisibility(
                visible = isExpanded,
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                Column {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Consult with a healthcare specialist or your personal practitioner for individual diagnosis and personalized care plans tailored to your specific health profile.",
                        fontSize = 13.sp,
                        lineHeight = 18.sp,
                        fontWeight = FontWeight.W400,
                        fontFamily = PlusJakartaSans,
                        color = BloomColors.Muted
                    )
                }
            }
        }
    }
}

@Composable
private fun BloomSectionTitle(title: String) {
    Text(
        text = title,
        fontSize = 18.sp,
        fontWeight = FontWeight.W600,
        fontStyle = FontStyle.Italic,
        fontFamily = Fraunces,
        color = BloomColors.Text,
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun BloomLearnScreenPreview() {
    MaterialTheme {
        BloomLearnScreen()
    }
}
