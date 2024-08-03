package com.example.external.datastore.note

import androidx.room.RoomDatabase
import com.example.external.setup.DbConnectionFactory
import com.example.external.setup.NotyDatabase
import com.example.external.setup.dao.note.NoteDAO
import com.example.external.setup.entity.note.Note
import javax.inject.Inject

class NoteService @Inject constructor(
    private val roomConnectionFactory: DbConnectionFactory<RoomDatabase>) {
    private val roomDb: RoomDatabase get() = roomConnectionFactory.databaseConnection
    private val noteDao: NoteDAO? get() = (roomDb as? NotyDatabase)?.getNoteDao()

    fun insert(entity: Note) {
        onRoom {noteDao?.insert(entity) }
    }

    fun insert(entities: List<Note>) {
         onRoom {
            noteDao?.insert(entities)
        }
    }


    fun delete(id: Int) {
         onRoom {
            noteDao?.delete(id)
        }
    }

    fun update(entity: Note) {
        onRoom {
            noteDao?.update(entity)
        }
    }

    fun getAll(): List<Note> {
        return onRoom {
            noteDao?.getAll()
        } ?: listOf()
    }

    private fun <R> onRoom(onRoom: () -> R): R {
        return onRoom.invoke()
    }
}