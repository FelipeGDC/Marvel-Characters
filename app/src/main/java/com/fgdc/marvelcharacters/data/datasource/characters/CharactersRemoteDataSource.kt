package com.fgdc.marvelcharacters.data.datasource.characters

import com.fgdc.marvelcharacters.data.datasource.core.entity.ApiResponse
import com.fgdc.marvelcharacters.data.datasource.core.CharacterMarvel
import retrofit2.Response

interface CharactersRemoteDataSource {
    suspend fun getAllCharacters(offset: Int): Response<ApiResponse<CharacterMarvel>>
    suspend fun getCharacterById(characterId: Int): Response<ApiResponse<CharacterMarvel>>
}
