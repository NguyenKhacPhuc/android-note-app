package com.example.android.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.domain.entity.NoteDomain
import com.example.domain.usecases.DeleteNoteUseCase
import com.example.domain.usecases.GetAllNoteUseCase
import com.example.domain.usecases.InsertNoteUseCase
import com.example.domain.usecases.UpdateNoteUseCase
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class NoteViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: NoteViewModel
    private val getAllNoteUseCase = mockk<GetAllNoteUseCase>(relaxed = true)
    private val insertNoteUseCase = mockk<InsertNoteUseCase>(relaxed = true)
    private val deleteNoteUseCase = mockk<DeleteNoteUseCase>(relaxed = true)
    private val updateNoteUseCase = mockk<UpdateNoteUseCase>(relaxed = true)

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        viewModel = NoteViewModel(
            getAllNoteUseCase,
            insertNoteUseCase,
            deleteNoteUseCase,
            updateNoteUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `test getAllNote success`() = runTest {
        val notes = listOf(
            NoteDomain(1, "Title1", "Content1", "2021-09-01", "#FFFFFF", "https://picsum.photos/200/300?random=1"),
            NoteDomain(2, "Title2", "Content2", "2021-09-02", "#000000", "https://picsum.photos/200/300?random=2")
        )
        coEvery { getAllNoteUseCase.getAllNoteUseCase() } returns flow { emit(notes) }

        val observer = mockk<Observer<List<NoteDomain>>>(relaxed = true)
        viewModel.listNote.observeForever(observer)

        viewModel.getAllNote()

        advanceUntilIdle()

        coVerify { observer.onChanged(notes) }
        assertEquals(notes, viewModel.listNote.value)
    }

    @Test
    fun `test insertNote`() = runTest {
        val note = NoteDomain(1, "Title", "Content", "2021-09-01", "#FFFFFF", "https://picsum.photos/200/300?random=1")

        viewModel.insertNote(note)

        advanceUntilIdle()

        coVerify { insertNoteUseCase.insertNote(note) }
    }

    @Test
    fun `test updateNote`() = runTest {
        val note = NoteDomain(1, "Title", "Content", "2021-09-01", "#FFFFFF", "https://picsum.photos/200/300?random=1")

        viewModel.updateNote(note)

        advanceUntilIdle()

        coVerify { updateNoteUseCase.updateNote(note) }
    }

    @Test
    fun `test deleteNote`() = runTest {
        val noteId = 1

        viewModel.deleteNote(noteId)

        advanceUntilIdle()

        coVerify { deleteNoteUseCase.deleteNote(noteId) }
    }
}
