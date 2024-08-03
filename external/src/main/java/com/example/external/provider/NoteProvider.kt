package com.example.external.provider

import com.example.domain.di.DefaultDispatcher
import com.example.domain.entity.NoteDomain
import com.example.domain.repository.NoteRepository
import com.example.external.datastore.note.NoteDataStoreFactory
import com.example.external.setup.entity.note.Note
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class NoteProvider @Inject constructor(
    private val noteDataStoreFactory: NoteDataStoreFactory,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) :
    BaseProvider(defaultDispatcher), NoteRepository {
    override suspend fun insert(note: NoteDomain) {
        callExternalService {
            noteDataStoreFactory.localDataStore().insert(note.toNote())
        }
    }

    override suspend fun insert(notes: List<NoteDomain>) {
        callExternalService {
            noteDataStoreFactory.localDataStore().insert(notes.map { it.toNote() })
        }
    }

    override suspend fun update(note: NoteDomain) {
        callExternalService {
            noteDataStoreFactory.localDataStore().update(note.toNote())
        }
    }

    override suspend fun delete(id: Int) {
        callExternalService {
            noteDataStoreFactory.localDataStore().delete(id)
        }
    }

    override suspend fun getAll(): Flow<List<NoteDomain>> {
        return flow {
            emit(noteDataStoreFactory.localDataStore().getAll().map { notes ->
                notes.toNoteDomain()
            }
            )
        }.flowOn(defaultDispatcher)
    }
}

private fun NoteDomain.toNote(): Note {
    return Note(
        id = id,
        title = title,
        content = content,
        createdDate = createdDate,
        hexColor = hexColor,
        imageLink = imageLink
    )
}

private fun Note.toNoteDomain(): NoteDomain {
    return NoteDomain(
        id = id,
        title = title,
        content = content,
        createdDate = createdDate,
        hexColor = hexColor,
        imageLink = imageLink
    )
}