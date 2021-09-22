package com.fgdc.marvelcharacters.data.datasource.characters

import com.fgdc.marvelcharacters.data.datasource.characters.api.CharactersApi
import com.fgdc.marvelcharacters.data.datasource.core.entity.ApiResponse
import com.fgdc.marvelcharacters.data.datasource.core.CharacterMarvel
import com.fgdc.marvelcharacters.utils.helpers.NetworkHandler
import retrofit2.Response
import javax.inject.Inject

class CharactersRemoteDataSourceImpl @Inject constructor(
    private val charactersApi: CharactersApi,
    private val networkHandler: NetworkHandler
) : CharactersRemoteDataSource {

    override suspend fun getAllCharacters(offset: Int): Response<ApiResponse<CharacterMarvel>> =
        charactersApi.getAllCharacters(offset)

    override suspend fun getCharacterById(characterId: Int): Response<ApiResponse<CharacterMarvel>> =
        charactersApi.getCharacterById(characterId)
}
