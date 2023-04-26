package com.example.audionotes.presentation.noteFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.audionotes.domain.AddNoteUseCase
import com.example.audionotes.domain.EditNoteUseCase
import com.example.audionotes.domain.GetNoteUseCase
import com.example.audionotes.domain.Note
import com.example.audionotes.utils.AudioPlayer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val getNoteUseCase: GetNoteUseCase,
    private val addNoteUseCase: AddNoteUseCase,
    private val editNoteUseCase: EditNoteUseCase
) : ViewModel() {

    private val audioPlayer = AudioPlayer()

    private val _note = MutableLiveData<Note>()
    val note: LiveData<Note>
        get() = _note

    private val _errorInputDesc = MutableLiveData<Boolean>()
    val errorInputDesc: LiveData<Boolean>
        get() = _errorInputDesc

    private val _shouldCloseScreen = MutableLiveData<Boolean>(false)
    val shouldCloseScreen: LiveData<Boolean>
        get() = _shouldCloseScreen

    fun getNote(noteId: Long) {
        viewModelScope.launch {
            val item = getNoteUseCase.execute(noteId)
            _note.value = item
        }
    }

    fun addTodoItem(inputDesc: String?, filename: String?) {
        val title = parseDesc(inputDesc)
        val validInput = isValidInput(title)
        viewModelScope.launch {
            if (validInput) {
                val item = Note(title = title, date = getCurrentTime(), audioSource = filename)
                addNoteUseCase.execute(item)
                _shouldCloseScreen.postValue(true)
            }
        }
    }

    fun playFile(filename: String?) {
        if (audioPlayer.isPlaying() && !audioPlayer.isEnd()) {
            audioPlayer.stop()
        } else {
            filename?.let { audioPlayer.start(it) }
        }
    }

    fun deleteFile(filename: String?) {
        val file = File(filename)
        if (file.exists() && (!audioPlayer.isPlaying() || audioPlayer.isEnd())) {
            file.delete()
        }
    }

    fun editTodoItem(inputDesc: String?, filename: String?) {
        val title = parseDesc(inputDesc)
        val validInput = isValidInput(title)
        if (validInput) {
            _note.value?.let {
                viewModelScope.launch {
                    val item = it.copy(title = title,
                    date = getCurrentTime(),
                    audioSource = filename)
                    editNoteUseCase.execute(item)
                    _shouldCloseScreen.postValue(true)
                }
            }

        }
    }

    fun isValidInput(inputDesc: String): Boolean {
        var result = true
        if (inputDesc.isBlank()) {
            _errorInputDesc.value = true
            result = false
        }
        return result
    }

    fun getCurrentTime(): String {
        val sdf = SimpleDateFormat("dd.MM-yyyy HH:mm")
        return sdf.format(Date())
    }

    private fun parseDesc(inputDesc: String?): String = inputDesc?.trim() ?: ""

    fun resetErrorInputDesc() {
        _errorInputDesc.value = false
    }
}