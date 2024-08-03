package com.example.external.setup.entity.note

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val content: String,
    val hexColor: String,
    val createdDate: String,
    val imageLink: String
)