package com.example.external.datastore.note

import com.example.external.setup.entity.note.Note
import javax.inject.Inject

class LocalNoteDataStore @Inject constructor(
    private val noteSerVice: NoteService
) : NoteDataStore {
    override fun insert(note: Note) {
        noteSerVice.insert(note)
    }

    override fun insert(notes: List<Note>) {
        return noteSerVice.insert(notes)
    }

    override fun update(note: Note) {
        noteSerVice.update(note)
    }

    override fun delete(id: Int) {
        noteSerVice.delete(id)
    }

    override fun getAll(): List<Note> {
        return noteSerVice.getAll()
    }
}