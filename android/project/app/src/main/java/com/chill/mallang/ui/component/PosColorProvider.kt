package com.chill.mallang.ui.component

import androidx.compose.ui.graphics.Color
import com.chill.mallang.ui.theme.Gray6

object PosColorProvider {
    private val colorMap =
        mapOf(
            "명사" to Color(0xFFE57373), // Red shade for Noun
            "대명사" to Color(0xFFBA68C8), // Purple shade for Pronoun
            "수사" to Color(0xFF7986CB), // Blue shade for Numeral
            "동사" to Color(0xFF64B5F6), // Light Blue shade for Verb
            "형용사" to Color(0xFF4DB6AC), // Teal shade for Adjective
            "관형사" to Color(0xFF81C784), // Green shade for Determiner
            "부사" to Color(0xFFFFD54F), // Yellow shade for Adverb
            "조사" to Color(0xFFFF8A65), // Orange shade for Postposition
            "감탄사" to Color(0xFFA1887F), // Brown shade for Interjection
        )

    fun getColorForPos(pos: String): Color {
        return colorMap[pos] ?: Gray6 // 기본 색상 반환
    }
}