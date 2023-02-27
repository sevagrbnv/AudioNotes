package com.example.audionotes.data.localDataSources

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.audionotes.data.NoteData

@Dao
interface NoteDao {
        @Insert(entity = NoteData::class, onConflict = OnConflictStrategy.REPLACE)
        suspend fun addNote(noteData: NoteData)

        @Query("DELETE FROM notes WHERE id = (:noteDateId)")
        suspend fun deleteNote(noteDateId: Int)

        @Query("SELECT * FROM notes WHERE id = (:noteDataId) LIMIT 1")
        suspend fun getNote(noteDataId: Int): NoteData

        @Query("SELECT * FROM notes")
        fun getTodoList(): LiveData<List<NoteData>>
}