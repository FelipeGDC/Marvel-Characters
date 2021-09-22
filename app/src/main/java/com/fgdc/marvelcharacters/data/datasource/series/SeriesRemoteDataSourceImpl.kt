package com.fgdc.marvelcharacters.data.datasource.series

import com.fgdc.marvelcharacters.data.datasource.core.entity.ApiResponse
import com.fgdc.marvelcharacters.data.datasource.core.SeriesMarvel
import com.fgdc.marvelcharacters.data.datasource.series.api.SeriesApi
import com.fgdc.marvelcharacters.utils.helpers.NetworkHandler
import retrofit2.Response
import javax.inject.Inject

class SeriesRemoteDataSourceImpl @Inject constructor(
    private val seriesApi: SeriesApi,
    private val networkHandler: NetworkHandler
) : SeriesRemoteDataSource {

    override suspend fun getSeriesById(seriesId: Int): Response<ApiResponse<SeriesMarvel>> =
        seriesApi.getSeriesById(seriesId)
}
