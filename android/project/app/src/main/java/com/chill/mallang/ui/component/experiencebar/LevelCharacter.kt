package com.chill.mallang.ui.component.experiencebar

import com.chill.mallang.R

object LevelCharacter {
    fun getResourceOfLevel(level: Int): Int =
        when (level) {
            in 1 .. 5 -> R.drawable.img_bronze
            in 6 .. 10 -> R.drawable.img_silver
            in 11..15 -> R.drawable.img_gold
            in 16..20 -> R.drawable.img_master
            else -> R.drawable.img_bronze
        }

    fun getLabelResourceOfLevel(level: Int): String {
        var step = level

        var label = when (level) {
            in 1..5 -> {
                "브론즈"
            }
            in 6..10 -> {
                step -= 5
                "실버"
            }
            in 11..15 -> {
                step -= 10
                "골드"
            }
            in 16..20 -> {
                step -= 15
                "마스터"
            }
            else -> {
                step -= 20
                "브론즈"
            }
        }

        return label + " " + step.toString()
    }
}
