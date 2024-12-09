package com.fgdc.marvelcharacters.characters.usecases

import com.fgdc.marvelcharacters.data.repositories.CharactersRepositoryImpl
import com.fgdc.marvelcharacters.domain.model.CharacterDetailDomain
import com.fgdc.marvelcharacters.domain.usecases.GetCharacterById
import com.fgdc.marvelcharacters.utils.functional.State
import com.fgdc.marvelcharacters.utils.mockApiResponse
import com.fgdc.marvelcharacters.utils.mockCharacters
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class GetCharacterByIdTest {

    @MockK
    lateinit var repository: CharactersRepositoryImpl

    private lateinit var getCharacterById: GetCharacterById

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        getCharacterById = GetCharacterById(repository)
    }

    @Test
    fun `should get specific character on success`() = runBlocking {
        val characterId = 0
        val character = mockCharacters(1)
        val mockResponse =
            flowOf(State.Success(mockApiResponse(character).apiData.results.map { it.toCharacterDetailDomain() }))
        coEvery {
            repository.getCharacterById(characterId)
        } returns mockResponse

        val flow: Flow<State<List<CharacterDetailDomain>>> =
            getCharacterById.run(GetCharacterById.Params(characterId))

        flow.collect { result ->
            assertIs<State.Success<List<CharacterDetailDomain>>>(result)
            assertEquals(character.map { it.toCharacterDetailDomain() }, result.data)
        }
        verify(atLeast = 1) { repository.getCharacterById(characterId) }
    }
}
