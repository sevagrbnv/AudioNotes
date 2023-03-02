package com.example.audionotes.domain

import android.util.Log
import androidx.lifecycle.LiveData
import javax.inject.Inject

class GetNoteListUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) {

    fun execute(): LiveData<List<Note>> {
        Log.e("Sosiski", "1123")
        return noteRepository.getNoteList()
    }
}