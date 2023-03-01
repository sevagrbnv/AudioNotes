package com.example.audionotes.utils

import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import android.util.Log
import java.io.File
import javax.inject.Inject

class AudioController @Inject constructor(
    private val context: Context
) {

    private var mediaRecorder: MediaRecorder? = null

    fun start(fileName: String) {
        Log.e("!!!!!", "start")

        val file = File(fileName)
        if (file.exists())
            file.delete()

        val mediaRecorder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            MediaRecorder(context)
        } else MediaRecorder()
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC)
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS)
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
        mediaRecorder.setOutputFile(fileName)
        mediaRecorder.prepare()
        mediaRecorder.start()

        this.mediaRecorder = mediaRecorder
    }

    fun stop() {
        if (mediaRecorder != null) {
            Log.e("!!!!!", "stop")
            try {
                mediaRecorder?.stop()
                mediaRecorder?.release()
            } catch (e: java.lang.Exception) {
                Log.e("!!!", "ttttttt")
            }
            mediaRecorder = null
        }
    }

    fun isRecording(): Boolean = mediaRecorder != null

    fun getVolume(): Int {

        return mediaRecorder?.maxAmplitude ?: 0
    }

}