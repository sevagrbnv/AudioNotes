package com.example.audionotes

import com.example.audionotes.domain.AddNoteUseCase
import com.example.audionotes.domain.Note
import com.example.audionotes.domain.NoteRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mockito

class AddItemUseCaseTest {

    @Test
    fun testAddItemUseCase() = runBlocking<Unit> {

        val data = Note(
            title = "title",
            date = "date",
            audioSource = "audiosource",
            id = 1
        )

        launch {
            val repository = Mockito.mock(NoteRepository::class.java)
            Mockito.`when`(repository.addNote(data)).thenReturn(Unit)
            AddNoteUseCase(repository = repository).execute(data)
            AddNoteUseCase(repository = repository).execute(data)
            Mockito.verify(repository, Mockito.times(2)).addNote(data)

        }
    }
}