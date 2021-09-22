package com.fgdc.marvelcharacters.series

import com.fgdc.marvelcharacters.data.datasource.series.SeriesRemoteDataSource
import com.fgdc.marvelcharacters.data.repositories.SeriesRepositoryImpl
import com.fgdc.marvelcharacters.domain.model.SeriesListDomain
import com.fgdc.marvelcharacters.utils.functional.State
import com.fgdc.marvelcharacters.utils.mockApiResponse
import com.fgdc.marvelcharacters.utils.mockSeries
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

class SeriesRepositoryTest {

    @MockK
    lateinit var seriesRemoteDataSource: SeriesRemoteDataSource

    private lateinit var repository: SeriesRepositoryImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        repository =
            SeriesRepositoryImpl(seriesRemoteDataSource)
    }

    @Test
    fun `should get specific series from service on success`(): Unit = runBlocking {

        val series = mockSeries(1)
        val mockResponse =
            State.Success(mockApiResponse(series).apiData.results.map { it.toSeriesListDomain() })
        val seriesId = 0

        coEvery {
            seriesRemoteDataSource.getSeriesById(seriesId)
        } returns mockResponse

        seriesRemoteDataSource.getSeriesById(seriesId) shouldBeEqualTo mockResponse

        val flow: Flow<State<List<SeriesListDomain>>> = repository.getSeriesById(seriesId)
        flow.collect { result ->
            result.`should be instance of`<State.Success<List<SeriesListDomain>>>()
            when (result) {
                is State.Success<List<SeriesListDomain>> -> {
                    result.data shouldBeEqualTo series.map { it.toSeriesListDomain() }
                }
            }
        }
    }
}
