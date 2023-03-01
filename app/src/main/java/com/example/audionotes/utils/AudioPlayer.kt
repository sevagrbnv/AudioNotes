package com.example.audionotes.utils

import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Build
import android.util.Log
import com.example.audionotes.presentation.noteFragment.NoteFragment
import java.io.File

class AudioPlayer {

    private var mediaPlayer: MediaPlayer? = null

    fun start(fileName: String) {
        Log.e("!!!!!", "start")

        val mediaPlayer = MediaPlayer()

        val file = File(fileName)
        if (file.exists()) {

            Log.e("!!!!!", fileName)
            mediaPlayer?.setDataSource(fileName)
            mediaPlayer?.prepare()
            mediaPlayer?.start()
        }

        this.mediaPlayer = mediaPlayer
    }

    fun stop() {
        if (mediaPlayer != null) {
            Log.e("!!!!!", "stop")
            try {
                mediaPlayer?.stop()
                mediaPlayer?.release()
            } catch (e: java.lang.Exception) {
                Log.e("!!!", "ttttttt")
            }
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