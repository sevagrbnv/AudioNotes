package com.example.audionotes.domain

import javax.inject.Inject

class AddNoteUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    suspend fun execute(note: Note) {
        repository.addNote(note)
    }
}