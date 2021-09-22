package com.fgdc.marvelcharacters.data.datasource.comics.api

import com.fgdc.marvelcharacters.data.datasource.core.entity.ApiResponse
import com.fgdc.marvelcharacters.data.datasource.core.ComicMarvel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ComicsApi {

    companion object {
        private const val COMICS_ENDPOINT = "comics"
    }

    @GET(COMICS_ENDPOINT.plus("/{comic_id}"))
    suspend fun getComicById(@Path("comic_id") comicId: Int): Response<ApiResponse<ComicMarvel>>
}
