package com.example.audionotes.presentation.noteFragment

import android.content.Context
import android.media.MediaMetadataRetriever
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.audionotes.R
import com.example.audionotes.databinding.FragmentNoteBinding
import com.example.audionotes.utils.AudioController
import com.example.audionotes.utils.AudioPlayer
import com.example.audionotes.utils.getDuration
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class NoteFragment : Fragment() {

    private lateinit var binding: FragmentNoteBinding

    private val viewModel by viewModels<NoteViewModel>()

    private lateinit var audioController: AudioController
    private lateinit var audioPlayer: AudioPlayer
    private var countDownTimer: CountDownTimer? = null

    private lateinit var filename: String

//    private var screenMode = UNKNOWN_MODE
//    private var itemId = com.example.simpletodo.domain.TodoItem.UNDEFINED_ID

    override fun onAttach(context: Context) {
        super.onAttach(context)
        audioController = AudioController(requireActivity())
        audioPlayer = AudioPlayer()
        filename = getPath(System.currentTimeMillis().toString())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTextChangeListener()
        //
        //startRightMode()
        startAddMode()
        //
        observeViewModel()

        binding.recordButton.setOnClickListener {
            onClickRecordButton(filename)
        }
        binding.playButton.setOnClickListener {
            onClickPlayButton(filename)
        }
        binding.deleteButton.setOnClickListener {
            val file = File(filename)
            if (file.exists() && (!audioPlayer.isPlaying() || audioPlayer.isEnd())) {
                file.delete()
            }
            binding.TimerNote.text = getString(R.string.no_record)
        }
    }

    private fun onClickPlayButton(filename: String) {
        if (audioPlayer.isPlaying() && !audioPlayer.isEnd()) {
            audioPlayer.stop()
        } else {
            audioPlayer.start(filename)
        }
    }


    private fun onClickRecordButton(filename: String) {
        if (audioController.isRecording()) {
            binding.recordButton.text = getString(R.string.record)
            binding.deleteButton.isEnabled = true
            binding.playButton.isEnabled = true
            audioController.stop()
            countDownTimer?.cancel()
            countDownTimer = null

            binding.TimerNote.text = getDuration(filename)
        } else {
            binding.deleteButton.isEnabled = false
            binding.playButton.isEnabled = false
            binding.recordButton.text = getString(R.string.stop)
            audioController.start(filename)
            countDownTimer = object : CountDownTimer(
                MAX_LENGTH_OF_AUDIO.toLong(),
                TIME_INTERVAL.toLong()
            ) {
                override fun onTick(p0: Long) {
                    handleVolume(audioController.getVolume())
                }
                override fun onFinish() {}
            }
            countDownTimer?.start()
        }
    }

    fun handleVolume(volume: Int) {
        var scale = volume / MAX_VOLUME.toFloat() + 1.0
        scale = Math.min(scale, 4.0)
        binding.audioButtonNote.animate()
            .scaleX(scale.toFloat())
            .scaleY(scale.toFloat())
            .setInterpolator(OvershootInterpolator())
            .setDuration(TIME_INTERVAL.toLong())
        //On pause elfkbnm
    }


    private fun observeViewModel() {
        viewModel.errorInputDesc.observe(viewLifecycleOwner) {
            val message = if (it) {
                "Пустая строка"
            } else {
                null
            }
            binding.tilDesc.error = message
        }
    }

    private fun setTextChangeListener() {
        binding.edTextDesc.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputDesc()
            }

        })
    }

    private fun startAddMode() {
        binding.saveButton.setOnClickListener {
            viewModel.addTodoItem(
                binding.edTextDesc.text?.toString(),
                filename
            )
            findNavController().popBackStack()
        }
    }

    private fun startRightMode() {
//        when(screenMode) {
//            EDIT_MODE -> startEditMode()
//            ADD_MODE -> startAddMode()
//        }
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer = null
    }

    fun getPath(filename: String): String {
        return "${requireContext().externalCacheDirs[0].absolutePath}/${filename}.wav"
    }

    companion object {
        private val MAX_VOLUME = 32768
        private val MAX_LENGTH_OF_AUDIO = 300_000
        private val TIME_INTERVAL = 100

    }
}