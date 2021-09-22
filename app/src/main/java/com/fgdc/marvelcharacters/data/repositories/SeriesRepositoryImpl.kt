package com.fgdc.marvelcharacters.data.repositories

import com.fgdc.marvelcharacters.data.datasource.series.SeriesRemoteDataSource
import com.fgdc.marvelcharacters.domain.model.SeriesListDomain
import com.fgdc.marvelcharacters.domain.repository.SeriesRepository
import com.fgdc.marvelcharacters.utils.exception.ErrorHandler
import com.fgdc.marvelcharacters.utils.functional.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SeriesRepositoryImpl @Inject constructor(
    private val seriesRemoteDataSource: SeriesRemoteDataSource
) : SeriesRepository {
    override fun getSeriesById(id: Int): Flow<State<List<SeriesListDomain>>> = flow {
        emit(seriesRemoteDataSource.getSeriesById(id))
    }.catch {
        it.printStackTrace()
        emit(State.Error(Throwable(ErrorHandler.UNKNOWN_ERROR)))
    }
}
