package com.example.audionotes.presentation.mainFragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.audionotes.domain.DeleteNoteUseCase
import com.example.audionotes.domain.GetNoteListUseCase
import com.example.audionotes.domain.Note
import com.example.audionotes.utils.FileContoller
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getNoteListUseCase: GetNoteListUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
): ViewModel() {

    val noteList = getNoteListUseCase.execute()

    fun deleteTodoItem(note: Note) {
        viewModelScope.launch {
            note.audioSource?.let { FileContoller().deleteFile(it) }
            deleteNoteUseCase.execute(note)
        }
    }

}