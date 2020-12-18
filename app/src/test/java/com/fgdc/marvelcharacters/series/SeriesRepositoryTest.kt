package com.fgdc.marvelcharacters.series

import com.fgdc.marvelcharacters.data.datasource.remote.services.SeriesService
import com.fgdc.marvelcharacters.data.repositories.SeriesRepositoryImpl
import com.fgdc.marvelcharacters.domain.model.SeriesListDomain
import com.fgdc.marvelcharacters.utils.mockApiResponse
import com.fgdc.marvelcharacters.utils.mockSeries
import com.fgdc.marvelcharacters.utils.functional.State
import com.fgdc.marvelcharacters.utils.functional.Success
import com.fgdc.marvelcharacters.utils.helpers.NetworkHandler
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.`should be instance of`
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test
import retrofit2.Response

class SeriesRepositoryTest {

    @Test
    fun `should get specific series from service on success`(): Unit = runBlocking {

        val series = mockSeries(1)
        val mockResponse = mockApiResponse(series)
        val seriesId = 0
        val service = mock<SeriesService> {
            onBlocking { getSeriesById(seriesId) } doReturn Response.success(mockResponse)
        }
        service.getSeriesById(seriesId).body() shouldBeEqualTo mockResponse

        val networkHandler = mock<NetworkHandler> {
            onBlocking { isConnected } doReturn true
        }
        val repository = SeriesRepositoryImpl(
            service,
            networkHandler
        )
        val flow: Flow<State<List<SeriesListDomain>>> = repository.getSeriesById(seriesId)
        flow.collect { result ->
            result.`should be instance of`<Success<List<SeriesListDomain>>>()
            when (result) {
                is Success<List<SeriesListDomain>> -> {
                    result.data shouldBeEqualTo series.map { it.toSeriesListDomain() }
                }
            }
        }
    }
}
