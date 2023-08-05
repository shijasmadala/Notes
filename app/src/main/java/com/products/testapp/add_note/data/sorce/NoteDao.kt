package com.products.testapp.add_note.data.sorce

import androidx.room.*
import com.products.testapp.add_note.data.entity.Notes
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNote(notes: Notes)

    @Delete
    suspend fun deleteNote(notes: Notes)

    @Update
    suspend fun updateNote(notes: Notes)

    @Query("SELECT * FROM notes")
     fun getNotes() : Flow<List<Notes>>

    @Query("SELECT * FROM notes WHERE id ==:id")
    fun getNoteById(id: Int) : Flow<Notes>
}