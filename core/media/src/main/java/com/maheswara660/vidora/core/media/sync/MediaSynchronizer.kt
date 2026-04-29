package com.maheswara660.vidora.core.media.sync

interface MediaSynchronizer {
    suspend fun refresh(path: String? = null): Boolean
    fun startSync()
    fun stopSync()
}
