package com.example.domain.usecases

import com.example.domain.entity.NoteDomain
import com.example.domain.repository.NoteRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class InsertBuiltInNotesUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) {
    suspend fun insertBuiltInNote(notes: List<NoteDomain>) {
        return noteRepository.insert(notes)
    }
}