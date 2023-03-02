package com.example.audionotes.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.audionotes.data.localDataSources.NoteDao
import com.example.audionotes.domain.Note
import com.example.audionotes.domain.NoteRepository
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao,
    private val mapper: NoteMapper
) : NoteRepository {

    override suspend fun addNote(note: Note) {
        noteDao.addNote(mapper.mapNoteToNoteData(note))
    }

    override suspend fun deleteNote(note: Note) {
        noteDao.deleteNote(note.id)
    }

    override suspend fun editNote(note: Note) {
        noteDao.addNote(mapper.mapNoteToNoteData(note))
    }

    override suspend fun getNote(noteDataId: Long): Note {
        val dbModel = noteDao.getNote(noteDataId)
        return mapper.mapNoteDataToNote(dbModel)
    }

    override fun getNoteList(): LiveData<List<Note>> = Transformations.map(
        noteDao.getTodoList()
    ) {
        Log.e("Sosiski", "12")
        mapper.mapListNoteDataToListNote(it)
    }
}