package com.fgdc.marvelcharacters.data.datasource.remote.services

import com.fgdc.marvelcharacters.data.datasource.remote.responses.ApiResponse
import com.fgdc.marvelcharacters.data.datasource.remote.responses.SeriesMarvel
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class SeriesService @Inject constructor(retrofit: Retrofit) : SeriesApi {

    private val seriesApi by lazy { retrofit.create(SeriesApi::class.java) }

    override suspend fun getSeriesById(seriesId: Int): Response<ApiResponse<SeriesMarvel>> =
        seriesApi.getSeriesById(seriesId)
}
