package com.fgdc.marvelcharacters.data.repositories

import com.fgdc.marvelcharacters.data.datasource.remote.services.SeriesApi
import com.fgdc.marvelcharacters.domain.model.SeriesListDomain
import com.fgdc.marvelcharacters.utils.exception.ErrorHandler
import com.fgdc.marvelcharacters.utils.functional.Error
import com.fgdc.marvelcharacters.utils.functional.ErrorNoConnection
import com.fgdc.marvelcharacters.utils.functional.State
import com.fgdc.marvelcharacters.utils.functional.Success
import com.fgdc.marvelcharacters.utils.helpers.NetworkHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SeriesRepositoryImpl @Inject constructor(
    private val apiService: SeriesApi,
    private val networkHandler: NetworkHandler
) : SeriesRepository {
    override fun getSeriesById(id: Int): Flow<State<List<SeriesListDomain>>> = flow {
        emit(
            if (networkHandler.isConnected == true) {
                apiService.getSeriesById(id).run {
                    if (isSuccessful && body() != null) {
                        Success(body()!!.apiData.results.map { it.toSeriesListDomain() })
                    } else {
                        Error(Throwable("s"))
                    }
                }
            } else {
                ErrorNoConnection(Throwable(ErrorHandler.NETWORK_ERROR_MESSAGE))
            }
        )
    }.catch {
        it.printStackTrace()
        emit(Error(Throwable("s")))
    }.flowOn(Dispatchers.IO)
}
