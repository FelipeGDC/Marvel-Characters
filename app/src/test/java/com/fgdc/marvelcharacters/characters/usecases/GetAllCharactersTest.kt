package com.fgdc.marvelcharacters.characters.usecases

import com.fgdc.marvelcharacters.data.repositories.CharactersRepositoryImpl
import com.fgdc.marvelcharacters.domain.model.CharacterListDomain
import com.fgdc.marvelcharacters.domain.usecases.GetAllCharacters
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
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue

class GetAllCharactersTest {

    @MockK
    lateinit var repository: CharactersRepositoryImpl

    private lateinit var getAllCharacters: GetAllCharacters

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        getAllCharacters = GetAllCharacters(repository)
    }

    @Test
    fun `should get all characters on success`() = runBlocking {
        val offset = 0
        val character = mockCharacters(20)
        val mockResponse =
            flowOf(State.Success(mockApiResponse(character).apiData.results.map { it.toCharacterListDomain() }))
        coEvery {
            repository.getAllCharacters(offset)
        } returns mockResponse

        val flow: Flow<State<List<CharacterListDomain>>> =
            getAllCharacters.run(GetAllCharacters.Params(offset))

        assertEquals(repository.getAllCharacters(offset), flow)
        flow.collect { result ->
            assertIs<State.Success<List<CharacterListDomain>>>(result)
            assertEquals(character.map { it.toCharacterListDomain() }, result.data)
        }
        verify(atLeast = 1) { repository.getAllCharacters(offset) }
    }
}
