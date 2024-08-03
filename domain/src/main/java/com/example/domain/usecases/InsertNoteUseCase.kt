package com.example.domain.usecases

import com.example.domain.entity.NoteDomain
import com.example.domain.repository.NoteRepository
import javax.inject.Inject

class InsertNoteUseCase@Inject constructor(
    private val noteRepository: NoteRepository
) {
    suspend fun insertNote(noteDomain: NoteDomain) {
        return noteRepository.insert(noteDomain)
    }
}