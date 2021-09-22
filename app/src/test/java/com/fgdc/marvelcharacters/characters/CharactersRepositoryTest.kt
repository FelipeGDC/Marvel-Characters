package com.fgdc.marvelcharacters.characters

import com.fgdc.data.datasource.remote.services.CharactersService
import com.fgdc.marvelcharacters.data.repositories.CharactersRepositoryImpl
import com.fgdc.marvelcharacters.domain.model.CharacterDetailDomain
import com.fgdc.marvelcharacters.domain.model.CharacterListDomain
import com.fgdc.marvelcharacters.utils.mockApiResponse
import com.fgdc.marvelcharacters.utils.mockCharacters
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

class CharactersRepositoryTest {

    @Test
    fun `should get all characters from service on success`(): Unit = runBlocking {

        val character = mockCharacters(20)
        val mockResponse = mockApiResponse(character)
        val offset = 0
        val service = mock<CharactersService> {
            onBlocking { getAllCharacters(offset) } doReturn Response.success(mockResponse)
        }

        val networkHandler = mock<NetworkHandler> {
            onBlocking { isConnected } doReturn true
        }
        val repository = CharactersRepositoryImpl(
            service,
            networkHandler
        )
        val flow: Flow<State<List<CharacterListDomain>>> =
            repository.getAllCharacters(offset)
        flow.collect { result ->
            result.`should be instance of`<Success<List<CharacterListDomain>>>()
            when (result) {
                is Success<List<CharacterListDomain>> -> {
                    result.data shouldBeEqualTo character.map { it.toCharacterListDomain() }
                }
            }
        }
    }

    @Test
    fun `should get specific character from service on success`(): Unit = runBlocking {

        val character = mockCharacters(1)
        val mockResponse = mockApiResponse(character)
        val characterId = 0
        val service = mock<CharactersService> {
            onBlocking { getCharacterById(characterId) } doReturn Response.success(mockResponse)
        }
        service.getCharacterById(characterId).body() shouldBeEqualTo mockResponse

        val networkHandler = mock<NetworkHandler> {
            onBlocking { isConnected } doReturn true
        }
        val repository = CharactersRepositoryImpl(
            service,
            networkHandler
        )
        val flow: Flow<State<List<CharacterDetailDomain>>> =
            repository.getCharacterById(characterId)
        flow.collect { result ->
            result.`should be instance of`<Success<List<CharacterDetailDomain>>>()
            when (result) {
                is Success<List<CharacterDetailDomain>> -> {
                    result.data shouldBeEqualTo character.map { it.toCharacterDetailDomain() }
                }
            }
        }
    }
}
