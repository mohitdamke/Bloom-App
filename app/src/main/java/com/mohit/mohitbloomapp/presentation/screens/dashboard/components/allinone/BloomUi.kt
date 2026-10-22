package com.mohit.mohitbloomapp.presentation.screens.dashboard.components.allinone

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mohit.mohitbloomapp.ui.theme.Fraunces

/* ------------------------------------------------------------------ */
/*  Colors (public, so every screen can reuse them)                    */
/* ------------------------------------------------------------------ */
object BloomUi {
    val Background = Color(0xFFFCF9F7)
    val CardPink = Color(0xFFFCEEEE)
    val Primary = Color(0xFFAD5750)
    val TextDark = Color(0xFF2B2320)
    val Muted = Color(0xFF8C7B74)
    val Border = Color(0xFFE6BFBA)
    val ButtonFill = Color(0xFFFCEAE8)
    val Link = Color(0xFF1A5FE0)
}

/* ------------------------------------------------------------------ */
/*  1. BloomText – one text component with presets                     */
/* ------------------------------------------------------------------ */
enum class BloomTextStyle(
    val size: TextUnit,
    val weight: FontWeight,
    val color: Color,
    val font: FontFamily? = null,
    val lineHeight: TextUnit = TextUnit.Unspecified
) {
    Display(36.sp, FontWeight.Bold, BloomUi.TextDark, Fraunces),
    Heading(17.sp, FontWeight.Bold, BloomUi.TextDark),
    Label(16.sp, FontWeight.Normal, BloomUi.Muted),
    Body(16.sp, FontWeight.Normal, BloomUi.Muted, null, 24.sp),
    Caption(14.sp, FontWeight.Normal, BloomUi.Muted),
    Unit(16.sp, FontWeight.SemiBold, BloomUi.Muted, Fraunces),
    Button(15.sp, FontWeight.Bold, BloomUi.Primary)
}

@Composable
fun BloomText(
    text: String,
    modifier: Modifier = Modifier,
    style: BloomTextStyle = BloomTextStyle.Body,
    // optional overrides – leave null to use the preset
    color: Color? = null,
    size: TextUnit? = null,
    weight: FontWeight? = null,
    align: TextAlign? = null,
    decoration: TextDecoration? = null
) {
    Text(
        text = text,
        modifier = modifier,
        color = color ?: style.color,
        fontSize = size ?: style.size,
        fontWeight = weight ?: style.weight,
        fontFamily = style.font,
        lineHeight = style.lineHeight,
        textAlign = align,
        textDecoration = decoration
    )
}

/* ------------------------------------------------------------------ */
/*  2. BloomLinkText – "Goal: 30 min · adjust in Settings"             */
/* ------------------------------------------------------------------ */
@Composable
fun BloomLinkText(
    prefix: String,
    linkText: String,
    onLinkClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        BloomText(text = prefix, style = BloomTextStyle.Caption)
        BloomText(
            text = linkText,
            modifier = Modifier.clickable(onClick = onLinkClick),
            style = BloomTextStyle.Caption,
            color = BloomUi.Link,
            weight = FontWeight.Bold,
            decoration = TextDecoration.Underline
        )
    }
}

/* ------------------------------------------------------------------ */
/*  3. BloomValueDisplay – the "0 min" time/number display             */
/* ------------------------------------------------------------------ */
@Composable
fun BloomValueDisplay(
    value: String,
    unit: String,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier, verticalAlignment = Alignment.Bottom) {
        BloomText(text = value, style = BloomTextStyle.Display)
        Spacer(modifier = Modifier.width(6.dp))
        BloomText(
            text = unit,
            modifier = Modifier.padding(bottom = 6.dp),
            style = BloomTextStyle.Unit
        )
    }
}

/* ------------------------------------------------------------------ */
/*  4. BloomRoundButton – the round  -  and  +  buttons                */
/* ------------------------------------------------------------------ */
@Composable
fun BloomRoundButton(
    icon: ImageVector,
    contentDescription: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: Dp = 60.dp
) {
    Surface(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .clickable(onClick = onClick),
        shape = CircleShape,
        color = BloomUi.ButtonFill,
        border = BorderStroke(1.5.dp, BloomUi.Border)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                tint = BloomUi.Primary,
                modifier = Modifier.size(size * 0.4f)
            )
        }
    }
}

/* ------------------------------------------------------------------ */
/*  5. BloomPillButton – the "+10 min" outlined pill                   */
/* ------------------------------------------------------------------ */
@Composable
fun BloomPillButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.clip(RoundedCornerShape(50)).clickable(onClick = onClick),
        shape = RoundedCornerShape(50),
        color = Color.White,
        border = BorderStroke(1.5.dp, BloomUi.Border)
    ) {
        BloomText(
            text = text,
            modifier = Modifier.padding(horizontal = 22.dp, vertical = 9.dp),
            style = BloomTextStyle.Button,
            align = TextAlign.Center
        )
    }
}

/* ------------------------------------------------------------------ */
/*  6. BloomStepper – "-  0 min  +"                                    */
/* ------------------------------------------------------------------ */
@Composable
fun BloomStepper(
    value: String,
    unit: String,
    minusIcon: ImageVector,
    plusIcon: ImageVector,
    onMinus: () -> Unit,
    onPlus: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        BloomRoundButton(icon = minusIcon, contentDescription = "Decrease", onClick = onMinus)
        BloomValueDisplay(value = value, unit = unit)
        BloomRoundButton(icon = plusIcon, contentDescription = "Increase", onClick = onPlus)
    }
}

/* ------------------------------------------------------------------ */
/*  7. BloomInfoCard – pink card with icon, title, body + action slot  */
/* ------------------------------------------------------------------ */
@Composable
fun BloomInfoCard(
    title: String,
    body: String,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    action: (@Composable () -> Unit)? = null
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        color = BloomUi.CardPink
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (icon != null) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = BloomUi.TextDark,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                }
                BloomText(text = title, style = BloomTextStyle.Heading)
            }
            Spacer(modifier = Modifier.height(6.dp))
            BloomText(text = body, style = BloomTextStyle.Body)
            if (action != null) {
                Spacer(modifier = Modifier.height(14.dp))
                action()
            }
        }
    }
}