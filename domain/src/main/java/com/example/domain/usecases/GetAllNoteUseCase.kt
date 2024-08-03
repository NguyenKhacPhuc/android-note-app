package com.example.domain.usecases

import com.example.domain.entity.NoteDomain
import com.example.domain.repository.NoteRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetAllNoteUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) {
    suspend fun getAllNoteUseCase(): Flow<List<NoteDomain>> {
        return noteRepository.getAll()
    }
}