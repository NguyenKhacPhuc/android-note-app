package com.example.domain.usecases

import com.example.domain.repository.NoteRepository
import javax.inject.Inject

class DeleteNoteUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) {
    suspend fun deleteNote(id: Int) {
        return noteRepository.delete(id)
    }
}
