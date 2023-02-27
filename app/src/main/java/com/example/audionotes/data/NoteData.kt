package com.example.audionotes.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class NoteData (
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val title: String,
    val date: String,
    val audioSource: String?
)