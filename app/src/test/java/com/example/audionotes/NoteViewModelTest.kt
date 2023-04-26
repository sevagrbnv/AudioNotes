package com.example.audionotes

import android.os.Looper
import com.example.audionotes.domain.AddNoteUseCase
import com.example.audionotes.domain.EditNoteUseCase
import com.example.audionotes.domain.GetNoteUseCase
import com.example.audionotes.presentation.noteFragment.NoteViewModel
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import java.text.SimpleDateFormat
import java.util.*

class NoteViewModelTest {

    private lateinit var viewModel: NoteViewModel
    private val mockLooper = Mockito.mock(Looper::class.java)

    @Before
    fun before() {
        val getNoteUseCase: GetNoteUseCase = Mockito.mock(GetNoteUseCase::class.java)
        val addNoteUseCase: AddNoteUseCase = Mockito.mock(AddNoteUseCase::class.java)
        val editNoteUseCase: EditNoteUseCase = Mockito.mock(EditNoteUseCase::class.java)
        viewModel = NoteViewModel(
            getNoteUseCase, addNoteUseCase, editNoteUseCase
        )
    }

    @Test
    fun CurrentTimeTest() {

        val sdf = SimpleDateFormat("dd.MM-yyyy HH:mm")
        assertEquals(viewModel.getCurrentTime(), sdf.format(Date()))
    }
}