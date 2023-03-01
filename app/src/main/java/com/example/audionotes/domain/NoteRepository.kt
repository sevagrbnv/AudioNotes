package com.example.audionotes.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations

interface NoteRepository {

    suspend fun addNote(note: Note)

    suspend fun deleteNote(note: Note)

    suspend fun editNote(note: Note)

    suspend fun getNote(noteDataId: Long): Note

    fun getNoteList(): LiveData<List<Note>>
}