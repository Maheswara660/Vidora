package com.maheswara660.vidora.core.database

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.maheswara660.vidora.core.database.dao.DirectoryDao
import com.maheswara660.vidora.core.database.dao.MediumDao

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    fun provideMediumDao(db: MediaDatabase): MediumDao = db.mediumDao()

    @Provides
    fun provideMediumStateDao(db: MediaDatabase) = db.mediumStateDao()

    @Provides
    fun provideDirectoryDao(db: MediaDatabase): DirectoryDao = db.directoryDao()
}
