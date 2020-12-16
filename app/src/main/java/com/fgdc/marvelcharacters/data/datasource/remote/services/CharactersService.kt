package com.fgdc.marvelcharacters.data.datasource.remote.services

import com.fgdc.marvelcharacters.data.datasource.remote.responses.ApiResponse
import com.fgdc.marvelcharacters.data.datasource.remote.responses.CharacterMarvel
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject


class CharactersService @Inject constructor(retrofit: Retrofit) : CharactersApi {

    private val charactersApi by lazy { retrofit.create(CharactersApi::class.java) }

    override suspend fun getAllCharacters(offset: Int): Response<ApiResponse<CharacterMarvel>> =
        charactersApi.getAllCharacters(offset)


    override suspend fun getCharacterById(characterId: Int): Response<ApiResponse<CharacterMarvel>> =
        charactersApi.getCharacterById(characterId)
}
