package com.chill.mallang.ui.feature.topbar

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
    ) : ViewModel() {
        var titleContent by mutableStateOf<@Composable () -> Unit>({})
            private set
        var onBack by mutableStateOf<(NavController) -> Unit>({})
            private set
        var onHome by mutableStateOf<(NavController) -> Unit>({})
            private set

        fun updateTitle(title: String) {
            titleContent = {
                Text(text = title, style = Typography.titleMedium)
            }
        }

        fun updateTitle(title: @Composable () -> Unit) {
            titleContent = title
        }

        fun updateOnBack(onBack: (NavController) -> Unit) {
            this.onBack = onBack
        }

        fun updateOnHome(onHome: (NavController) -> Unit) {
            this.onHome = onHome
        }
    }
