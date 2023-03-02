package com.example.audionotes.utils

import android.media.MediaMetadataRetriever

import java.io.File
import java.util.concurrent.TimeUnit

class FileContoller {

    fun getDuration(filename: String): String? {
        if (fileIsExist(filename)) {
            val mmr = MediaMetadataRetriever()
            mmr.setDataSource(filename)
            return mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
                ?.let { formatTime(it.toLong()) }
        } else return "Empty"
    }

    fun formatTime(millis: Long): String {
        return TimeUnit.MILLISECONDS.toSeconds(millis).toString() + "s"
    }

    fun fileIsExist(filename: String): Boolean {
        val file = File(filename)
        return file.exists()
    }

    fun deleteFile(filename: String) {
        val file = File(filename)
        if (file.exists()) file.delete()
    }
}