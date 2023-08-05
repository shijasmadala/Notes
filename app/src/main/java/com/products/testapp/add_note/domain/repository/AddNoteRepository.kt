package com.products.testapp.add_note.domain.repository

import com.products.testapp.add_note.data.entity.Notes
import com.products.testapp.add_note.domain.model.NotesModel
import kotlinx.coroutines.flow.Flow

interface AddNoteRepository {

    suspend fun insertNotes(notesModel: NotesModel)

     fun showNotes() : Flow<List<NotesModel>>

     fun getNotesById(noteId: Int) : Flow<NotesModel>

     suspend fun updateNote(notesModel: NotesModel)

     suspend fun deleteNote(notesModel: NotesModel)
}