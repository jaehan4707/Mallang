package com.chill.mallang.ui.component

import androidx.compose.ui.graphics.Color
import com.chill.mallang.ui.theme.Gray6

object PosColorProvider {
    private val colorMap =
        mapOf(
            "명사" to Color(0xFFE57373), // 빨강
            "대명사" to Color(0xFFBA68C8), // 보라
            "수사" to Color(0xFF7986CB), // 파랑
            "동사" to Color(0xFF64B5F6), // 하늘
            "형용사" to Color(0xFF4DB6AC), // 민트
            "관형사" to Color(0xFF81C784), // 초록
            "부사" to Color(0xFFFFD54F), // 노랑
            "조사" to Color(0xFFFF8A65), // 주황
            "감탄사" to Color(0xFFA1887F), // 갈색
        )

    fun getColorForPos(pos: String): Color {
        return colorMap[pos] ?: Gray6 // 기본 색상 반환
    }
}