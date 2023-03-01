package com.example.audionotes.domain

import javax.inject.Inject

class DeleteNoteUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    suspend fun execute(note: Note) {
        repository.deleteNote(note)
    }
}