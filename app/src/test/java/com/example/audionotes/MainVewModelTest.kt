package com.example.audionotes

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.audionotes.domain.DeleteNoteUseCase
import com.example.audionotes.domain.GetNoteListUseCase
import com.example.audionotes.domain.Note
import com.example.audionotes.presentation.mainFragment.MainViewModel
import com.example.audionotes.utils.FileContoller
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

class MainVewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var getNoteListUseCase: GetNoteListUseCase

    @Mock
    lateinit var deleteNoteUseCase: DeleteNoteUseCase

    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `noteList is initialized on viewModel creation`() {
        val noteList = listOf(
            Note(
                id = 1,
                title = "Test Note 1",
                date = "date",
                audioSource = "audiosource"
            ),
            Note(
                id = 2,
                title = "Test Note 2",
                date = "date",
                audioSource = "audiosource"
            )
        )
        val noteListLD = MutableLiveData<List<Note>>(noteList)
        whenever(getNoteListUseCase.execute()).thenReturn(noteListLD)
        viewModel = MainViewModel(getNoteListUseCase, deleteNoteUseCase)

        assertEquals(noteListLD, viewModel.noteList)
        verify(getNoteListUseCase).execute()
    }
}