package com.products.testapp.add_note.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.navArgs
import com.products.testapp.add_note.domain.model.NotesModel
import com.products.testapp.add_note.domain.repository.AddNoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class AddNoteViewModel @Inject constructor(private val addNoteRepository: AddNoteRepository,savedStateHandle: SavedStateHandle) :
    ViewModel() {

    private val _noteState = Channel<AddNoteState>()
    val noteState = _noteState.receiveAsFlow()
    private val noteId = savedStateHandle.get<Int>("noteId")

    init {
        if (noteId != 0){
            noteId?.let { getNoteById(it) }
        }
    }

    fun addNewNote(notesModel: NotesModel) = viewModelScope.launch {
        when {
            notesModel.title.isEmpty() -> {
                _noteState.send(AddNoteState.Error("You have to fill your note title"))
            }
            notesModel.contentNotes.isEmpty() -> {
                _noteState.send(AddNoteState.Error("You have to fill your note content"))
            }
            else -> {
                kotlin.runCatching {
                    addNoteRepository.insertNotes(notesModel)
                }.onSuccess {
                    _noteState.send(AddNoteState.AddNoteSuccess("Note Added"))
                }.onFailure {
                    it.message?.let { it1 -> AddNoteState.Error(it1) }
                        ?.let { it2 -> _noteState.send(it2) }
                }
            }
        }
    }

    private fun getNoteById(noteId: Int) = viewModelScope.launch{
        addNoteRepository.getNotesById(noteId).collectLatest {
            _noteState.send(AddNoteState.GetNoteById(it))
        }
    }

    fun updateUser(notesModel: NotesModel) = viewModelScope.launch {
        when {
            notesModel.title.isEmpty() -> _noteState.send(AddNoteState.Error("Please fill the title"))

            notesModel.contentNotes.isEmpty() -> _noteState.send(AddNoteState.Error("Please fill the content"))

            else -> {
                kotlin.runCatching {
                    addNoteRepository.updateNote(notesModel)
                }.onSuccess {
                    _noteState.send(AddNoteState.AddNoteSuccess("Note updated"))
                }.onFailure {
                    it.message?.let { it1 -> AddNoteState.Error(it1) }
                        ?.let { it2 -> _noteState.send(it2) }
                }
            }
        }
    }

    fun deleteNote(notesModel: NotesModel) = viewModelScope.launch {
        kotlin.runCatching {
            addNoteRepository.deleteNote(notesModel)
        }.onSuccess {
            _noteState.send(AddNoteState.AddNoteSuccess("Note deleted"))
        }.onFailure {  }
    }
}