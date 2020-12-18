package com.fgdc.marvelcharacters.characters.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.fgdc.marvelcharacters.data.repositories.CharactersRepositoryImpl
import com.fgdc.marvelcharacters.domain.model.CharacterListDomain
import com.fgdc.marvelcharacters.domain.usecases.GetAllCharacters
import com.fgdc.marvelcharacters.ui.charactersList.models.CharacterListView
import com.fgdc.marvelcharacters.ui.charactersList.viewmodel.CharactersListViewModel
import com.fgdc.marvelcharacters.utils.CoroutineTestRule
import com.fgdc.marvelcharacters.utils.functional.Error
import com.fgdc.marvelcharacters.utils.functional.Success
import com.fgdc.marvelcharacters.utils.mockCharacters
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException

class CharactersListViewModelTest {

    private lateinit var viewModel: CharactersListViewModel
    private lateinit var getAllCharacters: GetAllCharacters

    private var repository = mock<CharactersRepositoryImpl>()
    private val charactersObserver = mock<Observer<List<CharacterListView>>>()
    private val moreCharactersObserver = mock<Observer<List<CharacterListView>>>()
    private val isErrorObserver = mock<Observer<Throwable>>()
    private var initialOffset = 0
    private var maxOffset = 20

    @get:Rule
    var coroutinesRule = CoroutineTestRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        getAllCharacters = GetAllCharacters(repository)
        viewModel = CharactersListViewModel(getAllCharacters).apply {
            charactersResponse.observeForever(charactersObserver)
            moreCharactersResponse.observeForever(moreCharactersObserver)
            failure.observeForever(isErrorObserver)
        }
    }

    @Test
    fun `should emit get characters on success`() =
        coroutinesRule.dispatcher.runBlockingTest {
            val expectedCharacters = Success(mockCharacters(maxOffset).map { it.toCharacterListDomain() })

            val channel = Channel<Success<List<CharacterListDomain>>>()
            val flow = channel.consumeAsFlow()

            doReturn(flow)
                .whenever(repository)
                .getAllCharacters(initialOffset)

            launch {
                channel.send(expectedCharacters)
                channel.close(IOException())
            }

            viewModel.getAllCharacters()

            verify(charactersObserver).onChanged(expectedCharacters.data.map { it.toCharacterView() })
        }

    @Test
    fun `should emit get more characters on success`() =
        coroutinesRule.dispatcher.runBlockingTest {
            val expectedCharacters = Success(mockCharacters(maxOffset).map { it.toCharacterListDomain() })

            val channel = Channel<Success<List<CharacterListDomain>>>()
            val flow = channel.consumeAsFlow()

            doReturn(flow)
                .whenever(repository)
                .getAllCharacters(maxOffset)

            launch {
                channel.send(expectedCharacters)
                channel.close(IOException())
            }

            viewModel.charactersListScrolled()

            verify(moreCharactersObserver).onChanged(expectedCharacters.data.map { it.toCharacterView() })
        }

    @Test
    fun `should emit error on get characters lookup failure`() =
        coroutinesRule.dispatcher.runBlockingTest {

            val expectedCharacters = Success(mockCharacters(maxOffset).map { it.toCharacterListDomain() })
            val expectedError = Error(Throwable())

            val channel = Channel<Success<List<CharacterListDomain>>>()
            val flow = channel.consumeAsFlow()

            doReturn(flow)
                .whenever(repository)
                .getAllCharacters(initialOffset)

            launch {
                channel.send(expectedCharacters)
                channel.close(expectedError.exception)
            }

            viewModel.getAllCharacters()

            verify(charactersObserver).onChanged(expectedCharacters.data.map { it.toCharacterView() })
            verify(isErrorObserver).onChanged(expectedError.exception)
        }
}
