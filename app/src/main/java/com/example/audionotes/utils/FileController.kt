package com.example.audionotes.utils

import android.media.MediaMetadataRetriever
import java.util.concurrent.TimeUnit


fun getDuration(filename: String?): String? {
    val mmr = MediaMetadataRetriever()
    mmr.setDataSource(filename)
    return mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
        ?.let { formatTime(it.toLong()) }
}

fun formatTime(millis: Long): String {
    return TimeUnit.MILLISECONDS.toSeconds(millis).toString() + "s"
}