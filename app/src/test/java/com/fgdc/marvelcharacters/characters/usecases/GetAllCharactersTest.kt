package com.fgdc.marvelcharacters.characters.usecases

import com.fgdc.marvelcharacters.data.datasource.remote.services.CharactersService
import com.fgdc.marvelcharacters.data.repositories.CharactersRepositoryImpl
import com.fgdc.marvelcharacters.domain.model.CharacterListDomain
import com.fgdc.marvelcharacters.domain.usecases.GetAllCharacters
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

class GetAllCharactersTest {

    @Test
    fun `should get all characters on success`() = runBlocking {
        val repository: CharactersRepositoryImpl
        val getAllCharacters: GetAllCharacters
        val offset = 0
        val character = mockCharacters(20)
        val mockResponse = mockApiResponse(character)
        val service = mock<CharactersService> {
            onBlocking { getAllCharacters(offset) } doReturn Response.success(mockResponse)
        }

        val networkHandler = mock<NetworkHandler> {
            onBlocking { isConnected } doReturn true
        }

        repository = CharactersRepositoryImpl(service, networkHandler)
        getAllCharacters = GetAllCharacters(repository)

        val flow: Flow<State<List<CharacterListDomain>>> =
            getAllCharacters.run(GetAllCharacters.Params(offset))

        flow.collect { result ->
            result.`should be instance of`<Success<List<CharacterListDomain>>>()
            when (result) {
                is Success<List<CharacterListDomain>> -> {
                    result.data shouldBeEqualTo character.map { it.toCharacterListDomain() }
                }
            }
        }
    }
}
