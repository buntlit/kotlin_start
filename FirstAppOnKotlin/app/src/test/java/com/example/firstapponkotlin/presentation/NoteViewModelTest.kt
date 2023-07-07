package com.example.firstapponkotlin.presentation


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.firstapponkotlin.data.NotesRepository
import com.example.firstapponkotlin.model.Note
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.*
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class NoteViewModelTest {

    private val notesRepositoryMock = mockk<NotesRepository>()
    private lateinit var viewModel: NoteViewModel

    private var _resultLiveData = MutableLiveData(Result.success(Note()))
    private val resultLiveData: LiveData<Result<Note>> get() = _resultLiveData

    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        every { notesRepositoryMock.addOrReplace(any()) } returns resultLiveData
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `ViewModel Note title changed`() {
        val currentNote = Note(title = "Aza-za")
        viewModel = NoteViewModel(notesRepositoryMock, currentNote)
        viewModel.updateTitle("Ha-ha-ha")
        Assert.assertEquals("Ha-ha-ha", viewModel.note?.title)
    }

    @Test
    fun `NotesRepository addOrReplace Note with correct body text`() {
        viewModel = NoteViewModel(notesRepositoryMock, null)

        viewModel.updateNote("Hello!")
        viewModel.saveNote()

        verify(exactly = 1) { notesRepositoryMock.addOrReplace(match { it.note == "Hello!" }) }
    }

    @Test
    fun `LiveData contains no errors after save`() {
        val currentNote = Note(title = "Aza-za")
        viewModel = NoteViewModel(notesRepositoryMock, currentNote)
        viewModel.saveNote()

        Assert.assertTrue(viewModel.showError().value == null)
    }

    @Test
    fun `LiveData contains errors after save`() {
        every { notesRepositoryMock.addOrReplace(any()) } returns MutableLiveData(
            Result.failure(
                IllegalAccessError()
            )
        )

        val currentNote = Note(title = "Aza-za")
        viewModel = NoteViewModel(notesRepositoryMock, currentNote)
        viewModel.saveNote()

        Assert.assertTrue(viewModel.showError().value != null)
    }
}