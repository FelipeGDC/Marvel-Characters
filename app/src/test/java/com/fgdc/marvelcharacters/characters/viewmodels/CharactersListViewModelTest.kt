package com.fgdc.marvelcharacters.characters.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.fgdc.marvelcharacters.domain.usecases.GetAllCharacters
import com.fgdc.marvelcharacters.ui.charactersList.models.CharacterListView
import com.fgdc.marvelcharacters.ui.charactersList.viewmodel.CharactersListViewModel
import com.fgdc.marvelcharacters.utils.functional.State
import com.fgdc.marvelcharacters.utils.mockCharacters
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CharactersListViewModelTest {

    private lateinit var viewModel: CharactersListViewModel
    private val testDispatcher = TestCoroutineDispatcher()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var getAllCharacters: GetAllCharacters

    @MockK
    lateinit var charactersObserver: Observer<List<CharacterListView>>

    @MockK
    lateinit var moreCharactersObserver: Observer<List<CharacterListView>>

    private var maxOffset = 20

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        Dispatchers.setMain(testDispatcher)
        viewModel = CharactersListViewModel(getAllCharacters).apply {
            charactersResponse.observeForever(charactersObserver)
            moreCharactersResponse.observeForever(moreCharactersObserver)
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `should emit get characters on success`() = runBlockingTest {
        val expectedCharacters =
            mockCharacters(maxOffset).map { it.toCharacterListDomain() }
        val flowCharacter = flowOf(State.Success(expectedCharacters))

        coEvery { getAllCharacters.invoke(any()) } returns flowCharacter

        viewModel.getAllCharacters()

        verify {
            charactersObserver.onChanged(expectedCharacters.map { it.toCharacterView() })
        }
    }

    @Test
    fun `should emit get more characters on success`() = runBlockingTest {
        val expectedCharacters =
            mockCharacters(maxOffset).map { it.toCharacterListDomain() }
        val flowCharacter = flowOf(State.Success(expectedCharacters))

        coEvery { getAllCharacters.invoke(any()) } returns flowCharacter

        viewModel.charactersListScrolled()

        verify {
            moreCharactersObserver.onChanged(expectedCharacters.map { it.toCharacterView() })
        }
    }
}
