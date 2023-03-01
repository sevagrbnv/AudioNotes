package com.example.audionotes.data

import com.example.audionotes.domain.Note
import javax.inject.Inject

class NoteMapper @Inject constructor() {

    fun mapNoteToNoteData(note: Note) = NoteData(
        id = note.id,
        title = note.title,
        date = note.date,
        audioSource = note.audioSource
    )

    fun mapNoteDataToNote(noteData: NoteData) = Note(
        id = noteData.id,
        title = noteData.title,
        date = noteData.date,
        audioSource = noteData.audioSource
    )

    fun mapListNoteDataToListNote(list: List<NoteData>) = list.map {
        mapNoteDataToNote(it)
    }
}