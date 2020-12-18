package com.fgdc.marvelcharacters.data.datasource.remote.services

import com.fgdc.marvelcharacters.data.datasource.remote.responses.ApiResponse
import com.fgdc.marvelcharacters.data.datasource.remote.responses.CharacterMarvel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharactersApi {
    companion object {
        private const val CHARACTERS_ENDPOINT = "characters"
    }

    @GET(CHARACTERS_ENDPOINT)
    suspend fun getAllCharacters(@Query("offset") offset: Int): Response<ApiResponse<CharacterMarvel>>

    @GET(CHARACTERS_ENDPOINT.plus("/{character_id}"))
    suspend fun getCharacterById(@Path("character_id") characterId: Int): Response<ApiResponse<CharacterMarvel>>
}
