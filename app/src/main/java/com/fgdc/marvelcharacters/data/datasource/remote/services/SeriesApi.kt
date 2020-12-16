package com.fgdc.marvelcharacters.data.datasource.remote.services

import com.fgdc.marvelcharacters.data.datasource.remote.responses.ApiResponse
import com.fgdc.marvelcharacters.data.datasource.remote.responses.SeriesMarvel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface SeriesApi {

    companion object {
        private const val SERIES_ENDPOINT = "v1/public/series"
    }

    @GET(SERIES_ENDPOINT.plus("/{series_id}"))
    suspend fun getSeriesById(@Path("series_id") seriesId: Int): Response<ApiResponse<SeriesMarvel>>
}
