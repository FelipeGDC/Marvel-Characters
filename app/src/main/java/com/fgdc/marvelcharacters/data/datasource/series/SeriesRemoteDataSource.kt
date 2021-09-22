package com.fgdc.marvelcharacters.data.datasource.series

import com.fgdc.marvelcharacters.data.datasource.core.entity.ApiResponse
import com.fgdc.marvelcharacters.data.datasource.core.SeriesMarvel
import retrofit2.Response

interface SeriesRemoteDataSource {
    suspend fun getSeriesById(seriesId: Int): Response<ApiResponse<SeriesMarvel>>
}
