package com.fgdc.marvelcharacters.data.datasource.remote.services

import com.fgdc.marvelcharacters.data.datasource.remote.responses.ApiResponse
import com.fgdc.marvelcharacters.data.datasource.remote.responses.ComicMarvel
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class ComicsService @Inject constructor(retrofit: Retrofit) : ComicsApi {

    private val comicsApi by lazy { retrofit.create(ComicsApi::class.java) }

    override suspend fun getComicById(comicId: Int): Response<ApiResponse<ComicMarvel>> =
        comicsApi.getComicById(comicId)
}
