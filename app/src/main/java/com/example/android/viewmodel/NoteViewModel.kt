package com.example.android.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.NoteDomain
import com.example.domain.usecases.DeleteNoteUseCase
import com.example.domain.usecases.GetAllNoteUseCase
import com.example.domain.usecases.InsertNoteUseCase
import com.example.domain.usecases.UpdateNoteUseCase
import javax.inject.Inject
import kotlin.random.Random
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class NoteViewModel @Inject constructor(
    private val getAllNoteUseCase: GetAllNoteUseCase,
    private val insertNoteUseCase: InsertNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase
) :
    BaseViewModel() {

    private val mutableListNote = MutableLiveData<List<NoteDomain>>()
    val listNote: LiveData<List<NoteDomain>>
        get() = mutableListNote
    private val mutableIsLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean>
        get() = mutableIsLoading

    fun getAllNote() {
        viewModelScope.launch {
            getAllNoteUseCase.getAllNoteUseCase().onStart {
                mutableIsLoading.value = true
            }.collect { notes ->
                mutableIsLoading.value = false
                mutableListNote.value = notes.reversed()
            }
        }
    }

    fun insertNote(noteDomain: NoteDomain) {
        viewModelScope.launch {
            insertNoteUseCase.insertNote(noteDomain)
        }
    }

    fun updateNote(noteDomain: NoteDomain) {
        viewModelScope.launch {
            updateNoteUseCase.updateNote(noteDomain)
        }
    }

    fun deleteNote(id: Int) {
        viewModelScope.launch {
            deleteNoteUseCase.deleteNote(id)
        }
    }

    fun getRandomHexColor(): String {
        val chars = "0123456789ABCDEF"
        var color = "#"
        for (i in 1..6) {
            color += chars[Random.nextInt(chars.length)]
        }
        return color
    }
}