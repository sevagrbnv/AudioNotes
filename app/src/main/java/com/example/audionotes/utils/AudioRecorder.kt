package com.example.audionotes.utils

import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import javax.inject.Inject

class AudioRecorder @Inject constructor(
    private val context: Context
) {

    private var mediaRecorder: MediaRecorder? = null

    fun start(filename: String) {
        val fileContoller = FileContoller()
        fileContoller.deleteFile(filename)

        val mediaRecorder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            MediaRecorder(context)
        } else MediaRecorder()
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC)
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS)
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
        mediaRecorder.setOutputFile(filename)
        mediaRecorder.prepare()
        mediaRecorder.start()

        this.mediaRecorder = mediaRecorder
    }

    fun stop() {
        if (mediaRecorder != null) {
            try {
                mediaRecorder?.stop()
                mediaRecorder?.release()
            } catch (e: java.lang.Exception) {}
            mediaRecorder = null
        }
    }

    fun isRecording(): Boolean = mediaRecorder != null

    fun getVolume(): Int {

        return mediaRecorder?.maxAmplitude ?: 0
    }

}