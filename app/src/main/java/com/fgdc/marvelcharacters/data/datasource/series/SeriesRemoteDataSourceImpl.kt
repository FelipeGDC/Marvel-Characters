package com.fgdc.marvelcharacters.data.datasource.series

import com.fgdc.marvelcharacters.data.datasource.series.api.SeriesApi
import com.fgdc.marvelcharacters.domain.model.SeriesListDomain
import com.fgdc.marvelcharacters.utils.exception.ErrorHandler
import com.fgdc.marvelcharacters.utils.functional.State
import com.fgdc.marvelcharacters.utils.helpers.NetworkHandler
import javax.inject.Inject

class SeriesRemoteDataSourceImpl @Inject constructor(
    private val seriesApi: SeriesApi,
    private val networkHandler: NetworkHandler
) : SeriesRemoteDataSource {

    override suspend fun getSeriesById(seriesId: Int): State<List<SeriesListDomain>> {
        if (!networkHandler.isInternetAvailable()) {
            return State.ErrorNoConnection(Throwable(ErrorHandler.NETWORK_ERROR_MESSAGE))
        }
        return seriesApi.getSeriesById(seriesId).run {
            if (isSuccessful && body() != null) {
                State.Success(body()!!.apiData.results.map { it.toSeriesListDomain() })
            } else {
                State.BadRequest(Throwable(ErrorHandler.BAD_REQUEST))
            }
        }
    }
}
