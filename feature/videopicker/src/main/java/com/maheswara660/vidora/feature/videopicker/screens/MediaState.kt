package com.maheswara660.vidora.feature.videopicker.screens

import com.maheswara660.vidora.core.model.Folder

sealed interface MediaState {
    data object Loading : MediaState
    data class Success(val data: Folder?) : MediaState
}
