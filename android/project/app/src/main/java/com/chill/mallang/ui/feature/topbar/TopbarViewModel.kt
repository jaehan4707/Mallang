package com.chill.mallang.ui.feature.topbar

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.chill.mallang.ui.theme.Typography
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TopbarViewModel
    @Inject
    constructor(
        private val savedStateHandle: SavedStateHandle,
        private val topbarRepository: TopbarRepository,
    ) : ViewModel() {
        val isVisible = topbarRepository.isVisible
        val titleContent = topbarRepository.titleContent
        val onBack = topbarRepository.onBack
        val onHome = topbarRepository.onHome

        fun updateVisibility(isVisible: Boolean) {
            topbarRepository.updateVisibility(isVisible)
        }

        fun updateTitle(title: String) {
            topbarRepository.updateTitle { Text(text = title, style = Typography.titleMedium) }
        }

        fun updateTitle(title: @Composable () -> Unit) {
            topbarRepository.updateTitle(title)
        }

        fun updateOnBack(onBack: (NavController) -> Unit) {
            topbarRepository.updateOnBack(onBack)
        }

        fun updateOnHome(onHome: (NavController) -> Unit) {
            topbarRepository.updateOnHome(onHome)
        }
    }
