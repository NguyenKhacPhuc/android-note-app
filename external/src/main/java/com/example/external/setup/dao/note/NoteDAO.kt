package com.example.external.setup.dao.note

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.external.setup.entity.note.Note

@Dao
interface NoteDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insert(note: Note)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(note: List<Note>)

    @Update(entity = Note::class)
     fun update(note: Note): Int

    @Query("DELETE FROM Note WHERE id = :id")
    fun delete(id: Int)

    @Query("SELECT * FROM Note")
    fun getAll(): List<Note>
}