package com.example.domain.usecases

import com.example.domain.entity.NoteDomain
import com.example.domain.repository.NoteRepository
import javax.inject.Inject

class UpdateNoteUseCase @Inject constructor(private val repository: NoteRepository) {
    suspend fun updateNote(noteDomain: NoteDomain) {
        return repository.update(noteDomain)
    }
}