package com.products.testapp.add_note.presentation.show_note

import androidx.lifecycle.ViewModel
import com.products.testapp.add_note.domain.repository.AddNoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel

import javax.inject.Inject
@HiltViewModel
class HomeFragmentViewModel @Inject constructor(private val addNoteRepository: AddNoteRepository)
    : ViewModel() {
             val notes = addNoteRepository.showNotes()

}