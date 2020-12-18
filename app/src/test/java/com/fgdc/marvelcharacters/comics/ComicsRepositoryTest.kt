package com.fgdc.marvelcharacters.comics

import com.fgdc.marvelcharacters.data.datasource.remote.services.ComicsService
import com.fgdc.marvelcharacters.data.repositories.ComicsRepositoryImpl
import com.fgdc.marvelcharacters.domain.model.ComicListDomain
import com.fgdc.marvelcharacters.utils.mockApiResponse
import com.fgdc.marvelcharacters.utils.mockComics
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

class ComicsRepositoryTest {

    @Test
    fun `should get specific comic from service on success`(): Unit = runBlocking {

        val comic = mockComics(1)
        val mockResponse = mockApiResponse(comic)
        val comicId = 0
        val service = mock<ComicsService> {
            onBlocking { getComicById(comicId) } doReturn Response.success(mockResponse)
        }
        service.getComicById(comicId).body() shouldBeEqualTo mockResponse

        val networkHandler = mock<NetworkHandler> {
            onBlocking { isConnected } doReturn true
        }
        val repository = ComicsRepositoryImpl(
            service,
            networkHandler
        )
        val flow: Flow<State<List<ComicListDomain>>> = repository.getComicById(comicId)
        flow.collect { result ->
            result.`should be instance of`<Success<List<ComicListDomain>>>()
            when (result) {
                is Success<List<ComicListDomain>> -> {
                    result.data shouldBeEqualTo comic.map { it.toComicListDomain() }
                }
            }
        }
    }
}
