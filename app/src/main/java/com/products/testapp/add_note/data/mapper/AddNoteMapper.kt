package com.products.testapp.add_note.data.mapper

import com.products.testapp.add_note.data.entity.Notes
import com.products.testapp.add_note.domain.model.NotesModel

fun Notes.toNotesModel(): NotesModel {
    return NotesModel(
        id = id, title = title, contentNotes = noteContent
    )
}

fun NotesModel.toNotes(): Notes {
    return Notes(
        id = id, title = title, noteContent = contentNotes
    )
}
