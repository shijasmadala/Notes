package com.products.testapp.app.di

import android.app.Application
import androidx.room.Room
import com.products.testapp.add_note.data.sorce.NoteDao
import com.products.testapp.app.database.NoteDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideNoteDataBase(application: Application) = Room.databaseBuilder(
        application, NoteDataBase::class.java,"note_database"
    ).fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideNoteDao(noteDataBase: NoteDataBase) : NoteDao {
        return noteDataBase.noteDao
    }

}