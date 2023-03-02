package com.example.audionotes.domain

import javax.inject.Inject

class EditNoteUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    suspend fun execute(note: Note) {
        repository.editNote(note)
    }
}