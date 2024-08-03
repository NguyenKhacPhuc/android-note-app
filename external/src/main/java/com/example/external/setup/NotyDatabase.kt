package com.example.external.setup

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.external.setup.dao.note.NoteDAO
import com.example.external.setup.entity.note.Note

@Database(entities = [Note::class], version = 1)
abstract class NotyDatabase : RoomDatabase() {

    abstract fun getNoteDao(): NoteDAO

    companion object {

        private const val databaseName = "NotyDatabase"

        @Volatile
        private var instance: NotyDatabase? = null

        fun getDatabase(app: Application): NotyDatabase {
            return instance ?: synchronized(this) {
                val instance = Room.databaseBuilder(app, NotyDatabase::class.java, databaseName)
                    .build()
                this.instance = instance
                return instance
            }
        }
    }
}