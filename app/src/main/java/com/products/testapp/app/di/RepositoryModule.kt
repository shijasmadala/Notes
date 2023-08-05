package com.products.testapp.app.di

import com.products.testapp.add_note.data.repository.AddNoteRepositoryImpl
import com.products.testapp.add_note.domain.repository.AddNoteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindAddNoteRepository(addNoteRepositoryImpl: AddNoteRepositoryImpl) : AddNoteRepository
}