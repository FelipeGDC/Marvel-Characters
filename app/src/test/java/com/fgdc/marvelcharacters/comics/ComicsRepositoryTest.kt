package com.fgdc.marvelcharacters.comics

import com.fgdc.marvelcharacters.data.datasource.comics.ComicsRemoteDataSource
import com.fgdc.marvelcharacters.data.repositories.ComicsRepositoryImpl
import com.fgdc.marvelcharacters.domain.model.ComicListDomain
import com.fgdc.marvelcharacters.utils.functional.State
import com.fgdc.marvelcharacters.utils.mockApiResponse
import com.fgdc.marvelcharacters.utils.mockComics
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.`should be instance of`
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Test

class ComicsRepositoryTest {

    @MockK
    lateinit var comicRemoteDataSource: ComicsRemoteDataSource

    private lateinit var repository: ComicsRepositoryImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        repository =
            ComicsRepositoryImpl(comicRemoteDataSource)
    }

    @Test
    fun `should get specific comic from service on success`(): Unit = runBlocking {

        val comic = mockComics(1)
        val mockResponse =
            State.Success(mockApiResponse(comic).apiData.results.map { it.toComicListDomain() })
        val comicId = 0

        coEvery {
            comicRemoteDataSource.getComicById(comicId)
        } returns mockResponse

        comicRemoteDataSource.getComicById(comicId) shouldBeEqualTo mockResponse

        val flow: Flow<State<List<ComicListDomain>>> = repository.getComicById(comicId)
        flow.collect { result ->
            result.`should be instance of`<State.Success<List<ComicListDomain>>>()
            when (result) {
                is State.Success<List<ComicListDomain>> -> {
                    result.data shouldBeEqualTo comic.map { it.toComicListDomain() }
                }
            }
        }
    }
}
