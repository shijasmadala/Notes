package com.products.testapp.add_note.data.repository

import com.products.testapp.add_note.data.entity.Notes
import com.products.testapp.add_note.data.mapper.toNotes
import com.products.testapp.add_note.data.mapper.toNotesModel
import com.products.testapp.add_note.data.sorce.NoteDao
import com.products.testapp.add_note.domain.model.NotesModel
import com.products.testapp.add_note.domain.repository.AddNoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AddNoteRepositoryImpl @Inject constructor(private val noteDao: NoteDao):AddNoteRepository  {
    override suspend fun insertNotes(notesModel: NotesModel) {
        kotlin.runCatching {
            noteDao.addNote(notesModel.toNotes())
        }.onSuccess {

        }.onFailure {

        }
    }

    override  fun showNotes(): Flow<List<NotesModel>> {
        return noteDao.getNotes().map { it ->
            it.map { it.toNotesModel() }
        }
    }

    override fun getNotesById(noteId: Int): Flow<NotesModel> {
        return noteDao.getNoteById(noteId).map {
            it.toNotesModel()
        }
    }

    override suspend fun updateNote(notesModel: NotesModel) {
        kotlin.runCatching {
            noteDao.updateNote(notesModel.toNotes())
        }.onSuccess {

        }.onFailure {

        }
    }

    override suspend fun deleteNote(notesModel: NotesModel) {
        kotlin.runCatching {
            noteDao.deleteNote(notesModel.toNotes())
        }
    }


}