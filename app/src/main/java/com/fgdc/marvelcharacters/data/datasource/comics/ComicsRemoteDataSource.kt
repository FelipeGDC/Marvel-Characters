package com.fgdc.marvelcharacters.data.datasource.comics

import com.fgdc.marvelcharacters.data.datasource.core.entity.ApiResponse
import com.fgdc.marvelcharacters.data.datasource.core.ComicMarvel
import retrofit2.Response

interface ComicsRemoteDataSource {
    suspend fun getComicById(comicId: Int): Response<ApiResponse<ComicMarvel>>
}
