package com.fgdc.marvelcharacters.data.datasource.series.api

import com.fgdc.marvelcharacters.data.datasource.core.entity.ApiResponse
import com.fgdc.marvelcharacters.data.datasource.core.SeriesMarvel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface SeriesApi {

    companion object {
        private const val SERIES_ENDPOINT = "series"
    }

    @GET(SERIES_ENDPOINT.plus("/{series_id}"))
    suspend fun getSeriesById(@Path("series_id") seriesId: Int): Response<ApiResponse<SeriesMarvel>>
}
