package com.products.testapp.add_note.presentation

import com.products.testapp.add_note.domain.model.NotesModel

sealed class AddNoteState {
    data class AddNoteSuccess(
        val message: String,

        ) : AddNoteState()

    data class UpdateNote(val message: String) : AddNoteState()
    data class DeleteNote(val message: String) : AddNoteState()
    data class GetNoteById(val notesModel: NotesModel):AddNoteState()
    data class Error(val message: String) : AddNoteState()
    object Loading : AddNoteState()
    object Empty : AddNoteState()
}
