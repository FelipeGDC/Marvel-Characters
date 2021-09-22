package com.fgdc.marvelcharacters.characters.usecases

import com.fgdc.data.datasource.remote.services.CharactersService
import com.fgdc.marvelcharacters.data.repositories.CharactersRepositoryImpl
import com.fgdc.marvelcharacters.domain.model.CharacterDetailDomain
import com.fgdc.marvelcharacters.domain.usecases.GetCharacterById
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

class GetCharacterByIdTest {

    @Test
    fun `should get specific character on success`() = runBlocking {
        val repository: CharactersRepositoryImpl
        val getCharacterById: GetCharacterById
        val characterId = 0
        val character = mockCharacters(1)
        val mockResponse = mockApiResponse(character)
        val service = mock<CharactersService> {
            onBlocking { getCharacterById(characterId) } doReturn Response.success(mockResponse)
        }

        val networkHandler = mock<NetworkHandler> {
            onBlocking { isConnected } doReturn true
        }

        repository = CharactersRepositoryImpl(service, networkHandler)
        getCharacterById = GetCharacterById(repository)

        val flow: Flow<State<List<CharacterDetailDomain>>> =
            getCharacterById.run(GetCharacterById.Params(characterId))

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
