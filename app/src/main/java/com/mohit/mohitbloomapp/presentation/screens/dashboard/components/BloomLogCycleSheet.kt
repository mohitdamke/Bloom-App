package com.mohit.mohitbloomapp.presentation.screens.dashboard.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mohit.mohitbloomapp.ui.theme.BloomColors
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BloomLogCycleSheet(
    initialDate: LocalDate = LocalDate.now(),
    onDone: (LocalDate) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = initialDate
            .atStartOfDay(ZoneId.of("UTC"))
            .toInstant()
            .toEpochMilli(),
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                val selectedDate = Instant.ofEpochMilli(utcTimeMillis)
                    .atZone(ZoneId.of("UTC"))
                    .toLocalDate()

                return !selectedDate.isAfter(LocalDate.now())
            }
        }
    )

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = BloomColors.Background,
        shape = RoundedCornerShape(
            topStart = 28.dp,
            topEnd = 28.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(bottom = 24.dp)
        ) {
            Row {
                Icon(
                    imageVector = Icons.Outlined.CalendarMonth,
                    contentDescription = null,
                    tint = BloomColors.Primary
                )

                Spacer(Modifier.width(10.dp))

                Text(
                    text = "Log Cycle",
                    fontSize = 21.sp,
                    color = BloomColors.Text
                )
            }

            Spacer(Modifier.height(8.dp))

            Text(
                text = "When did your last period start?",
                fontSize = 14.sp,
                color = BloomColors.Muted
            )

            Spacer(Modifier.height(12.dp))

            DatePicker(
                state = datePickerState,
                title = null,
                headline = null,
                showModeToggle = false,
                colors = DatePickerDefaults.colors(
                    containerColor = BloomColors.Background,
                    selectedDayContainerColor = BloomColors.Primary,
                    todayDateBorderColor = BloomColors.Primary
                )
            )

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val date = Instant.ofEpochMilli(millis)
                            .atZone(ZoneId.of("UTC"))
                            .toLocalDate()

                        onDone(date)
                    }
                },
                enabled = datePickerState.selectedDateMillis != null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = BloomColors.Primary
                )
            ) {
                Text(
                    text = "Done",
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
        }
    }
}