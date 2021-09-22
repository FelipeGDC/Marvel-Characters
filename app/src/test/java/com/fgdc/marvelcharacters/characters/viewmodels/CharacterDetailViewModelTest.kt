package com.fgdc.marvelcharacters.characters.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.fgdc.marvelcharacters.domain.usecases.GetCharacterById
import com.fgdc.marvelcharacters.domain.usecases.GetComicById
import com.fgdc.marvelcharacters.domain.usecases.GetSeriesById
import com.fgdc.marvelcharacters.ui.characterDetail.models.CharacterDetailView
import com.fgdc.marvelcharacters.ui.characterDetail.models.ComicListView
import com.fgdc.marvelcharacters.ui.characterDetail.models.SeriesListView
import com.fgdc.marvelcharacters.ui.characterDetail.viewmodel.CharacterDetailViewModel
import com.fgdc.marvelcharacters.utils.functional.State
import com.fgdc.marvelcharacters.utils.mockCharacters
import com.fgdc.marvelcharacters.utils.mockComics
import com.fgdc.marvelcharacters.utils.mockSeries
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.verifyOrder
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
class CharacterDetailViewModelTest {

    private lateinit var viewModel: CharacterDetailViewModel
    private val testDispatcher = TestCoroutineDispatcher()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var getCharacter: GetCharacterById

    @MockK
    lateinit var getComics: GetComicById

    @MockK
    lateinit var getSeries: GetSeriesById

    @MockK
    lateinit var characterObserver: Observer<CharacterDetailView>

    @MockK
    lateinit var comicsObserver: Observer<List<ComicListView>>

    @MockK
    lateinit var seriesObserver: Observer<List<SeriesListView>>

    private val characterId = 0
    private val expectedCharacters = mockCharacters(1).map { it.toCharacterDetailDomain() }
    private val flowCharacters = flowOf(State.Success(expectedCharacters))

    private val expectedSeries = mockSeries(1).map { it.toSeriesListDomain() }
    private val flowSeries = flowOf(State.Success(expectedSeries))

    private val expectedComic = mockComics(1).map { it.toComicListDomain() }
    private val flowComic = flowOf(State.Success(expectedComic))

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        Dispatchers.setMain(testDispatcher)
        coEvery { getComics.invoke(any()) } returns flowComic
        coEvery { getSeries.invoke(any()) } returns flowSeries
        coEvery { getCharacter.invoke(any()) } returns flowCharacters
        viewModel = CharacterDetailViewModel(getCharacter, getComics, getSeries).apply {
            characterDetailResponse.observeForever(characterObserver)
            comicsListResponse.observeForever(comicsObserver)
            seriesListResponse.observeForever(seriesObserver)
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `should emit get character, get series and get comic by id on success`() = runBlockingTest {
        viewModel.getCharacterById(characterId)

        val characters = expectedCharacters.map { it.toCharacterDetailView() }[0]
        characters.comics.addAll(expectedComic.map { it.toComicListView() }.toMutableList())

        verifyOrder {
            comicsObserver.onChanged(expectedComic.map { it.toComicListView() })
            seriesObserver.onChanged(expectedSeries.map { it.toSeriesListView() })
            characterObserver.onChanged(characters)
        }
    }
}
