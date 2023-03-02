package com.example.audionotes.di

import android.content.Context
import androidx.room.Room
import com.example.audionotes.data.NoteMapper
import com.example.audionotes.data.NoteRepositoryImpl
import com.example.audionotes.data.localDataSources.AppDatabase
import com.example.audionotes.data.localDataSources.NoteDao
import com.example.audionotes.domain.NoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideRepositoty(
        noteDao: NoteDao,
        mapper: NoteMapper
    ): NoteRepository {
        return NoteRepositoryImpl(
            noteDao = noteDao,
            mapper = mapper
        )
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app.db"
        ).build()

    @Provides
    fun provideDao(appDatabase: AppDatabase): NoteDao {
        return appDatabase.noteDao()
    }
}