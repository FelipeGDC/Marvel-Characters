package com.fgdc.marvelcharacters.characters

import com.fgdc.marvelcharacters.data.datasource.characters.CharactersRemoteDataSource
import com.fgdc.marvelcharacters.data.repositories.CharactersRepositoryImpl
import com.fgdc.marvelcharacters.domain.model.CharacterDetailDomain
import com.fgdc.marvelcharacters.domain.model.CharacterListDomain
import com.fgdc.marvelcharacters.utils.functional.State
import com.fgdc.marvelcharacters.utils.mockApiResponse
import com.fgdc.marvelcharacters.utils.mockCharacters
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

class CharactersRepositoryTest {

    @MockK
    lateinit var charactersRemoteDataSource: CharactersRemoteDataSource

    private lateinit var repository: CharactersRepositoryImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        repository =
            CharactersRepositoryImpl(charactersRemoteDataSource)
    }

    @Test
    fun `should get all characters from service on success`(): Unit = runBlocking {

        val character = mockCharacters(20)
        val mockResponse =
            State.Success(mockApiResponse(character).apiData.results.map { it.toCharacterListDomain() })
        val offset = 0

        coEvery {
            charactersRemoteDataSource.getAllCharacters(offset)
        } returns mockResponse

        charactersRemoteDataSource.getAllCharacters(offset) shouldBeEqualTo mockResponse

        val flow: Flow<State<List<CharacterListDomain>>> =
            repository.getAllCharacters(offset)
        flow.collect { result ->
            result.`should be instance of`<State.Success<List<CharacterListDomain>>>()
            when (result) {
                is State.Success<List<CharacterListDomain>> -> {
                    result.data shouldBeEqualTo character.map { it.toCharacterListDomain() }
                }
            }
        }
    }

    @Test
    fun `should get specific character from service on success`(): Unit = runBlocking {

        val character = mockCharacters(1)
        val mockResponse =
            State.Success(mockApiResponse(character).apiData.results.map { it.toCharacterDetailDomain() })
        val characterId = 0

        coEvery {
            charactersRemoteDataSource.getCharacterById(characterId)
        } returns mockResponse

        charactersRemoteDataSource.getCharacterById(characterId) shouldBeEqualTo mockResponse

        val flow: Flow<State<List<CharacterDetailDomain>>> =
            repository.getCharacterById(characterId)
        flow.collect { result ->
            result.`should be instance of`<State.Success<List<CharacterDetailDomain>>>()
            when (result) {
                is State.Success<List<CharacterDetailDomain>> -> {
                    result.data shouldBeEqualTo character.map { it.toCharacterDetailDomain() }
                }
            }
        }
    }
}
