package com.example.audionotes.domain

import javax.inject.Inject

class GetNoteUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    suspend fun execute(noteId: Long): Note = repository.getNote(noteId)
}