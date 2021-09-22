package com.fgdc.marvelcharacters.data.datasource.comics

import com.fgdc.marvelcharacters.data.datasource.comics.api.ComicsApi
import com.fgdc.marvelcharacters.data.datasource.core.entity.ApiResponse
import com.fgdc.marvelcharacters.data.datasource.core.ComicMarvel
import com.fgdc.marvelcharacters.utils.helpers.NetworkHandler
import retrofit2.Response
import javax.inject.Inject

class ComicsRemoteDataSourceImpl @Inject constructor(
    private val comicsApi: ComicsApi,
    private val networkHandler: NetworkHandler
) : ComicsRemoteDataSource {

    override suspend fun getComicById(comicId: Int): Response<ApiResponse<ComicMarvel>> =
        comicsApi.getComicById(comicId)
}
