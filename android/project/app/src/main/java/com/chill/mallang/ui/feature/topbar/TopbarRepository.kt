package com.chill.mallang.ui.feature.topbar

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.navigation.NavController
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TopbarRepository
    @Inject
    constructor() {
        private val _isVisible = mutableStateOf(false)
        val isVisible: State<Boolean> = _isVisible
        private val _titleContent = mutableStateOf<@Composable () -> Unit>({ Text(text = "title") })
        val titleContent: State<@Composable () -> Unit> = _titleContent
        private val _onBack = mutableStateOf<(NavController) -> Unit>({})
        val onBack: State<(NavController) -> Unit> = _onBack
        private val _onHome = mutableStateOf<(NavController) -> Unit>({})
        val onHome: State<(NavController) -> Unit> = _onHome

        fun updateVisibility(isVisible: Boolean) {
            _isVisible.value = isVisible
        }

        fun updateTitle(title: @Composable () -> Unit) {
            _titleContent.value = title
        }

        fun updateOnBack(onBack: (NavController) -> Unit) {
            _onBack.value = onBack
        }

        fun updateOnHome(onHome: (NavController) -> Unit) {
            _onHome.value = onHome
        }
    }
