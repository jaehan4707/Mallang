package com.chill.mallang.ui.feature.select

import androidx.compose.runtime.Stable
import com.chill.mallang.data.model.entity.Faction
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

@Stable
data class SignUpUiState(
    val factionsStatus: PersistentList<Faction> = persistentListOf(),
)
