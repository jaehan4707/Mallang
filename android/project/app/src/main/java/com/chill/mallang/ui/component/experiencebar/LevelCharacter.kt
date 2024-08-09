package com.chill.mallang.ui.component.experiencebar

import com.chill.mallang.R

object LevelCharacter {
    fun getResourceOfLevel(level: Int): Int =
        when (level) {
            1 -> R.drawable.ic_logo
            2 -> R.drawable.ic_logo
            3 -> R.drawable.ic_logo
            4 -> R.drawable.ic_logo
            5 -> R.drawable.ic_logo
            else -> R.drawable.ic_logo
        }
}
