package com.maheswara660.vidora.settings.screens.appearance

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.maheswara660.vidora.core.data.repository.PreferencesRepository
import com.maheswara660.vidora.core.model.ApplicationPreferences
import com.maheswara660.vidora.core.model.ThemeConfig
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class AppearancePreferencesViewModel @Inject constructor(
    private val preferencesRepository: PreferencesRepository,
) : ViewModel() {

    private val uiStateInternal = MutableStateFlow(
        AppearancePreferencesUiState(
            preferences = preferencesRepository.applicationPreferences.value,
        ),
    )
    val uiState = uiStateInternal.asStateFlow()

    init {
        viewModelScope.launch {
            preferencesRepository.applicationPreferences.collect { preferences ->
                uiStateInternal.update { it.copy(preferences = preferences) }
            }
        }
    }

    fun onEvent(event: AppearancePreferencesEvent) {
        when (event) {
            is AppearancePreferencesEvent.UpdateThemeConfig -> updateThemeConfig(event.themeConfig)
            is AppearancePreferencesEvent.UpdateAccentColorIndex -> updateAccentColorIndex(event.index)
        }
    }

    private fun updateThemeConfig(themeConfig: ThemeConfig) {
        viewModelScope.launch {
            preferencesRepository.updateApplicationPreferences {
                it.copy(themeConfig = themeConfig)
            }
        }
    }

    private fun updateAccentColorIndex(index: Int) {
        viewModelScope.launch {
            preferencesRepository.updateApplicationPreferences {
                it.copy(accentColorIndex = index)
            }
        }
    }
}

@Stable
data class AppearancePreferencesUiState(
    val preferences: ApplicationPreferences = ApplicationPreferences(),
)

sealed interface AppearancePreferencesEvent {
    data class UpdateThemeConfig(val themeConfig: ThemeConfig) : AppearancePreferencesEvent
    data class UpdateAccentColorIndex(val index: Int) : AppearancePreferencesEvent
}
