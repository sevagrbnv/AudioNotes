package com.example.audionotes.utils

import android.media.MediaPlayer
import java.io.File

class AudioPlayer {

    private var mediaPlayer: MediaPlayer? = null

    fun start(fileName: String) {

        val mediaPlayer = MediaPlayer()

        val file = File(fileName)
        if (file.exists()) {
            mediaPlayer.setDataSource(fileName)
            mediaPlayer.prepare()
            mediaPlayer.start()
        }

        this.mediaPlayer = mediaPlayer
    }

    fun stop() {
        if (mediaPlayer != null) {
            try {
                mediaPlayer?.stop()
                mediaPlayer?.release()
            } catch (e: java.lang.Exception) {}
            mediaPlayer = null
        }
    }

    fun isEnd(): Boolean {
        if (mediaPlayer != null) {
            val current = mediaPlayer!!.currentPosition
            val size = getSize()
            return current == size
        } else throw RuntimeException("You need to check file for exist")
    }

    fun getSize(): Int {
        if (mediaPlayer != null)
            return mediaPlayer!!.duration
        else throw RuntimeException("You need to check file for exist")
    }

    fun isPlaying(): Boolean = mediaPlayer != null

}