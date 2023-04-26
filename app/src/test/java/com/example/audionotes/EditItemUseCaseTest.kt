package com.example.audionotes

import com.example.audionotes.domain.AddNoteUseCase
import com.example.audionotes.domain.EditNoteUseCase
import com.example.audionotes.domain.Note
import com.example.audionotes.domain.NoteRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mockito

class EditItemUseCaseTest {
    @Test
    fun testEditItemUseCase() = runBlocking<Unit> {

        val data = Note(
            title = "title",
            date = "date",
            audioSource = "audiosource",
            id = 1
        )

        launch {
            val repository = Mockito.mock(NoteRepository::class.java)
            Mockito.`when`(repository.editNote(data)).thenReturn(Unit)
            EditNoteUseCase(repository = repository).execute(data)
            EditNoteUseCase(repository = repository).execute(data)
            Mockito.verify(repository, Mockito.times(2)).editNote(data)

        }
    }
}