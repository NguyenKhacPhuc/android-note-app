package com.example.domain.repository

import com.example.domain.entity.NoteDomain
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    suspend fun insert(note: NoteDomain)
    suspend fun update(note: NoteDomain)
    suspend fun delete(id: Int)
    suspend fun getAll(): Flow<List<NoteDomain>>
    suspend fun insert(notes: List<NoteDomain>)
}