package com.fgdc.marvelcharacters.characters.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.fgdc.marvelcharacters.data.repositories.CharactersRepositoryImpl
import com.fgdc.marvelcharacters.data.repositories.ComicsRepositoryImpl
import com.fgdc.marvelcharacters.data.repositories.SeriesRepositoryImpl
import com.fgdc.marvelcharacters.domain.model.CharacterDetailDomain
import com.fgdc.marvelcharacters.domain.model.ComicListDomain
import com.fgdc.marvelcharacters.domain.model.SeriesListDomain
import com.fgdc.marvelcharacters.domain.usecases.GetCharacterById
import com.fgdc.marvelcharacters.domain.usecases.GetComicById
import com.fgdc.marvelcharacters.domain.usecases.GetSeriesById
import com.fgdc.marvelcharacters.ui.characterDetail.models.CharacterDetailView
import com.fgdc.marvelcharacters.ui.characterDetail.models.ComicListView
import com.fgdc.marvelcharacters.ui.characterDetail.models.SeriesListView
import com.fgdc.marvelcharacters.ui.characterDetail.viewmodel.CharacterDetailViewModel
import com.fgdc.marvelcharacters.utils.CoroutineTestRule
import com.fgdc.marvelcharacters.utils.functional.Error
import com.fgdc.marvelcharacters.utils.functional.Success
import com.fgdc.marvelcharacters.utils.mockCharacters
import com.fgdc.marvelcharacters.utils.mockComics
import com.fgdc.marvelcharacters.utils.mockSeries
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

class CharacterDetailViewModelTest {

    private lateinit var viewModel: CharacterDetailViewModel
    private lateinit var getCharacter: GetCharacterById
    private lateinit var getComic: GetComicById
    private lateinit var getSeries: GetSeriesById

    private var charactersRepository = mock<CharactersRepositoryImpl>()
    private var comicsRepository = mock<ComicsRepositoryImpl>()
    private var seriesRepository = mock<SeriesRepositoryImpl>()

    private val characterObserver = mock<Observer<CharacterDetailView>>()
    private val comicsObserver = mock<Observer<List<ComicListView>>>()
    private val seriesObserver = mock<Observer<List<SeriesListView>>>()
    private val isErrorObserver = mock<Observer<Throwable>>()

    @get:Rule
    var coroutinesRule = CoroutineTestRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        getCharacter = GetCharacterById(charactersRepository)
        getComic = GetComicById(comicsRepository)
        getSeries = GetSeriesById(seriesRepository)
        viewModel = CharacterDetailViewModel(getCharacter, getComic, getSeries).apply {
            characterDetailResponse.observeForever(characterObserver)
            comicsListResponse.observeForever(comicsObserver)
            seriesListResponse.observeForever(seriesObserver)
            failure.observeForever(isErrorObserver)
        }
    }

    @Test
    fun `should emit get character by id on success`() =
        coroutinesRule.dispatcher.runBlockingTest {
            val characterId = 0
            val expectedCharacters =
                Success(mockCharacters(1).map { it.toCharacterDetailDomain() })

            val channel = Channel<Success<List<CharacterDetailDomain>>>()
            val flow = channel.consumeAsFlow()

            doReturn(flow)
                .whenever(charactersRepository)
                .getCharacterById(characterId)

            launch {
                channel.send(expectedCharacters)
                channel.close(IOException())
            }

            viewModel.getCharacterById(characterId)

            verify(characterObserver).onChanged(expectedCharacters.data.map { it.toCharacterDetailView() }[0])
        }

    @Test
    fun `should emit error on character by id lookup failure`() =
        coroutinesRule.dispatcher.runBlockingTest {

            val characterId = 0
            val expectedCharacters =
                Success(mockCharacters(1).map { it.toCharacterDetailDomain() })
            val expectedError = Error(Throwable())

            val channel = Channel<Success<List<CharacterDetailDomain>>>()
            val flow = channel.consumeAsFlow()

            doReturn(flow)
                .whenever(charactersRepository)
                .getCharacterById(characterId)

            launch {
                channel.send(expectedCharacters)
                channel.close(expectedError.exception)
            }

            viewModel.getCharacterById(characterId)

            verify(characterObserver).onChanged(expectedCharacters.data.map { it.toCharacterDetailView() }[0])
            verify(isErrorObserver).onChanged(expectedError.exception)
        }

    @Test
    fun `should emit get comic by id on success`() =
        coroutinesRule.dispatcher.runBlockingTest {
            val comicId = 0

            val expectedComic =
                Success(mockComics(1).map { it.toComicListDomain() })

            val channel = Channel<Success<List<ComicListDomain>>>()
            val flow = channel.consumeAsFlow()

            doReturn(flow)
                .whenever(comicsRepository)
                .getComicById(comicId)

            launch {
                channel.send(expectedComic)
                channel.close(IOException())
            }

            viewModel.getComicById(comicId)

            verify(comicsObserver).onChanged(expectedComic.data.map { it.toComicListView() })
        }

    @Test
    fun `should emit get series by id on success`() =
        coroutinesRule.dispatcher.runBlockingTest {
            val seriesId = 0

            val expectedSeries =
                Success(mockSeries(1).map { it.toSeriesListDomain() })

            val channel = Channel<Success<List<SeriesListDomain>>>()
            val flow = channel.consumeAsFlow()

            doReturn(flow)
                .whenever(seriesRepository)
                .getSeriesById(seriesId)

            launch {
                channel.send(expectedSeries)
                channel.close(IOException())
            }

            viewModel.getSeriesById(seriesId)

            verify(seriesObserver).onChanged(expectedSeries.data.map { it.toSeriesListView() })
        }
}
