package com.example.audionotes.presentation.noteFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.audionotes.domain.AddNoteUseCase
import com.example.audionotes.domain.EditNoteUseCase
import com.example.audionotes.domain.GetNoteUseCase
import com.example.audionotes.domain.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val getNoteUseCase: GetNoteUseCase,
    private val addNoteUseCase: AddNoteUseCase,
    private val editNoteUseCase: EditNoteUseCase
) : ViewModel() {

    private val _note = MutableLiveData<Note>()
    val note: LiveData<Note>
        get() = _note

    private val _errorInputDesc = MutableLiveData<Boolean>()
    val errorInputDesc: LiveData<Boolean>
        get() = _errorInputDesc

    fun getNote(noteId: Long) {
        viewModelScope.launch {
            val item = getNoteUseCase.execute(noteId)
            _note.value = item
        }
    }

    fun addTodoItem(inputDesc: String?, filename: String?) {
        val title = parseDesc(inputDesc)
        val validInput = isValidInput(title)
        if (validInput) {
            viewModelScope.launch {
                val item = Note(title = title, date = getCurrentTime(), audioSource = filename)
                addNoteUseCase.execute(item)
            }
        }
    }

    private fun isValidInput(inputDesc: String): Boolean {
        var result = true
        if (inputDesc.isBlank())  {
            _errorInputDesc.value = true
            result = false
        }
        return result
    }

    private fun getCurrentTime(): String {
        val sdf = SimpleDateFormat("dd.MM-yyyy HH:mm")
        return sdf.format(Date())
    }



    private fun parseDesc(inputDesc: String?): String = inputDesc?.trim() ?: ""

    fun resetErrorInputDesc() {
        _errorInputDesc.value = false
    }
}