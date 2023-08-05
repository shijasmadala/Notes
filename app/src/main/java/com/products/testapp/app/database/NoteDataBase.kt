package com.products.testapp.app.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.products.testapp.add_note.data.sorce.NoteDao
import com.products.testapp.add_note.data.entity.Notes

@Database(entities = [Notes::class], version = 1)
abstract class NoteDataBase:RoomDatabase() {
    abstract val noteDao: NoteDao
}