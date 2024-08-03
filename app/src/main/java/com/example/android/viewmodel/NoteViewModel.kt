package com.example.android.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.NoteDomain
import com.example.domain.usecases.DeleteNoteUseCase
import com.example.domain.usecases.GetAllNoteUseCase
import com.example.domain.usecases.InsertBuiltInNotesUseCase
import com.example.domain.usecases.InsertNoteUseCase
import com.example.domain.usecases.UpdateNoteUseCase
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

@Singleton
class NoteViewModel @Inject constructor(
    private val getAllNoteUseCase: GetAllNoteUseCase,
    private val insertBuiltInNotesUseCase: InsertBuiltInNotesUseCase,
    private val insertNoteUseCase: InsertNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase
) :
    BaseViewModel() {

    private val mutableListNote = MutableLiveData<List<NoteDomain>>()
    val listNote: LiveData<List<NoteDomain>>
        get() = mutableListNote

    fun getAllNote() {
        viewModelScope.launch {
            getAllNoteUseCase.getAllNoteUseCase().onStart { }.collect { notes ->
                mutableListNote.value = notes
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

    private fun generateFakeNotes(): List<NoteDomain> {
        val fakeNotes = mutableListOf<NoteDomain>()
        val imageUrls = listOf(
            "https://picsum.photos/200/300?random=1",
            "https://picsum.photos/200/300?random=2",
            "https://picsum.photos/200/300?random=3",
            "https://picsum.photos/200/300?random=4",
            "https://picsum.photos/200/300?random=5",
            "https://picsum.photos/200/300?random=6",
            "https://picsum.photos/200/300?random=7",
            "https://picsum.photos/200/300?random=8",
            "https://picsum.photos/200/300?random=9",
            "https://picsum.photos/200/300?random=10"
        )
        val randomNames = generateRandomNames(10)
        val randomSpeeches = generateRandomSpeeches(10)
        for (i in 1..10) {
            val randomName = randomNames[Random.nextInt(randomNames.size)]
            val randomContent = randomSpeeches[Random.nextInt(randomSpeeches.size)]
            val note = NoteDomain(
                id = i,
                title = randomName,
                content = randomContent,
                createdDate = "2021-09-${if (i < 10) "0$i" else i}",
                hexColor = getRandomHexColor(),
                imageLink = imageUrls[Random.nextInt(imageUrls.size)]
            )
            fakeNotes.add(note)
        }
        return fakeNotes
    }

    fun getRandomHexColor(): String {
        val chars = "0123456789ABCDEF"
        var color = "#"
        for (i in 1..6) {
            color += chars[Random.nextInt(chars.length)]
        }
        return color
    }

    private fun generateRandomNames(count: Int): List<String> {
        val firstNames = listOf(
            "John",
            "Jane",
            "Alex",
            "Emily",
            "Chris",
            "Katie",
            "Michael",
            "Sarah",
            "David",
            "Laura"
        )
        val lastNames = listOf(
            "Smith",
            "Johnson",
            "Williams",
            "Brown",
            "Jones",
            "Garcia",
            "Miller",
            "Davis",
            "Rodriguez",
            "Martinez"
        )

        val randomNames = mutableListOf<String>()
        repeat(count) {
            val firstName = firstNames[Random.nextInt(firstNames.size)]
            val lastName = lastNames[Random.nextInt(lastNames.size)]
            randomNames.add("$firstName $lastName")
        }
        return randomNames
    }

    private fun generateRandomSpeeches(count: Int): List<String> {
        val phrases = listOf(
            "Ladies and gentlemen,",
            "In conclusion,",
            "Furthermore,",
            "As we all know,",
            "It is important to note that,",
            "In light of recent events,",
            "To summarize,",
            "On behalf of everyone here,",
            "With great pleasure,",
            "In the spirit of unity,"
        )

        val sentences = listOf(
            "we must strive for excellence in all that we do.",
            "our efforts will lead us to success.",
            "this is a momentous occasion.",
            "we are gathered here to celebrate.",
            "let us not forget the importance of our mission.",
            "we have achieved great things together.",
            "the future holds many opportunities.",
            "we must remain vigilant and dedicated.",
            "our hard work is paying off.",
            "we are on the path to greatness."
        )

        val paragraphs = listOf(
            "Today marks a significant milestone in our journey. We have come a long way, and our achievements are a testament to our hard work and dedication. Let us continue to push forward and strive for even greater success.",
            "As we reflect on our accomplishments, it is clear that our collective efforts have made a difference. We have faced challenges head-on and emerged stronger. Together, we can overcome any obstacle and reach new heights.",
            "The road ahead may be long and arduous, but with determination and perseverance, we will prevail. Our commitment to excellence will guide us, and our unity will be our strength. Let us move forward with confidence and purpose.",
            "In these times of uncertainty, it is more important than ever to stay focused on our goals. We must remain resilient and adaptable, ready to face whatever comes our way. Our resolve will see us through, and our vision will lead us to success.",
            "We stand at the threshold of a new era, filled with possibilities and opportunities. Our journey has been marked by hard work and dedication, and our future is bright. Let us embrace the challenges ahead and work together to achieve our dreams."
        )

        val randomSpeeches = mutableListOf<String>()
        repeat(count) {
            val speech = StringBuilder()
            speech.append(phrases[Random.nextInt(phrases.size)]).append(" ")
            speech.append(sentences[Random.nextInt(sentences.size)]).append(" ")
            speech.append(paragraphs[Random.nextInt(paragraphs.size)])
            randomSpeeches.add(speech.toString())
        }
        return randomSpeeches
    }
}