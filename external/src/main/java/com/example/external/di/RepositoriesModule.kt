package com.example.external.di

import com.example.domain.repository.NoteRepository
import com.example.external.provider.NoteProvider
import dagger.Binds
import dagger.Module

@Module
@Suppress("UNUSED")
abstract class RepositoriesModule {

    @Binds
    abstract fun bindRepositoryNote(repositoryNote: NoteProvider): NoteRepository
}
