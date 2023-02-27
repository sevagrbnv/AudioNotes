package com.example.audionotes.domain

data class Note(
    val id: Long,
    val title: String,
    val date: String,
    val audioSource: String?
)
