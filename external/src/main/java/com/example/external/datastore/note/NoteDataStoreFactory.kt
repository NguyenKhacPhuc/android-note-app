package com.example.external.datastore.note

import javax.inject.Inject

class NoteDataStoreFactory @Inject constructor(
    private val localDataStore: LocalNoteDataStore
) {

    fun localDataStore(): LocalNoteDataStore = localDataStore
}