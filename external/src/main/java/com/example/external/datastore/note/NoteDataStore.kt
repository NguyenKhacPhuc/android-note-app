package com.example.external.datastore.note

import com.example.external.setup.entity.note.Note

interface NoteDataStore {
    fun insert(note: Note)

    fun update(note: Note)

    fun delete(id: Int)

    fun getAll(): List<Note>

    fun insert(notes: List<Note>)
}