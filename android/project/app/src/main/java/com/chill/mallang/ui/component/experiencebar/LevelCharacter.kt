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
}
