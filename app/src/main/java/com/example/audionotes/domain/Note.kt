package com.example.audionotes.domain

data class Note(
    val title: String,
    val date: String,
    val audioSource: String?,
    var id: Long = UNDEFINED_ID
) {
    companion object {
        const val UNDEFINED_ID = 0L
    }
}