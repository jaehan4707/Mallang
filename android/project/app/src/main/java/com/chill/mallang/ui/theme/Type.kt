package com.chill.mallang.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.chill.mallang.R

val galmuri11Family = FontFamily(
    Font(R.font.galmuri11, FontWeight.Normal),
    Font(R.font.galmuri11, FontWeight.SemiBold)
)

val omuyPrettyFamily = FontFamily(
    Font(R.font.omyu_pretty, FontWeight.Normal),
    Font(R.font.omyu_pretty, FontWeight.SemiBold),
)

private val galmuri11Headline1 = TextStyle(
    fontFamily = galmuri11Family,
    fontWeight = FontWeight.SemiBold,
    fontSize = 28.sp,
    letterSpacing = (-0.07).em
)

private val galmuri11Headline2 = TextStyle(
    fontFamily = galmuri11Family,
    fontWeight = FontWeight.SemiBold,
    fontSize = 24.sp,
    letterSpacing = (-0.07).em
)

private val galmuri11Body1 = TextStyle(
    fontFamily = galmuri11Family,
    fontWeight = FontWeight.Medium,
    fontSize = 18.sp,
    letterSpacing = (-0.07).em
)

private val galmuri11Body2 = TextStyle(
    fontFamily = galmuri11Family,
    fontWeight = FontWeight.Medium,
    fontSize = 16.sp,
    letterSpacing = (-0.07).em
)

private val galmuri11Body3 = TextStyle(
    fontFamily = omuyPrettyFamily,
    fontWeight = FontWeight.Medium,
    fontSize = 14.sp,
    letterSpacing = (-0.07).em
)

private val omyuPrettyHeadline1 = TextStyle(
    fontFamily = omuyPrettyFamily,
    fontWeight = FontWeight.SemiBold,
    fontSize = 28.sp,
    letterSpacing = (-0.07).em
)

private val omyuPrettyHeadline2 = TextStyle(
    fontFamily = omuyPrettyFamily,
    fontWeight = FontWeight.SemiBold,
    fontSize = 24.sp,
    letterSpacing = (-0.07).em
)


private val omyuPrettyBody1 = TextStyle(
    fontFamily = omuyPrettyFamily,
    fontWeight = FontWeight.Medium,
    fontSize = 18.sp,
    letterSpacing = (-0.07).em
)

private val omyuPrettyBody2 = TextStyle(
    fontFamily = omuyPrettyFamily,
    fontWeight = FontWeight.Medium,
    fontSize = 16.sp,
    letterSpacing = (-0.07).em
)

private val omyuPrettyBody3 = TextStyle(
    fontFamily = omuyPrettyFamily,
    fontWeight = FontWeight.Medium,
    fontSize = 14.sp,
    letterSpacing = (-0.07).em
)


val Typography = Typography(
    titleLarge = galmuri11Headline1,
    titleMedium = galmuri11Headline2,
    headlineLarge = omyuPrettyHeadline1,
    headlineMedium = omyuPrettyHeadline2,
    bodyLarge = galmuri11Body1,
    bodyMedium = galmuri11Body2,
    bodySmall = galmuri11Body3,
    displayLarge = omyuPrettyBody1,
    displayMedium = omyuPrettyBody2,
    displaySmall = omyuPrettyBody3,
)
