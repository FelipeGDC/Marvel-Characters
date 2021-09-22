package com.fgdc.marvelcharacters.data.repositories

import com.fgdc.marvelcharacters.data.datasource.comics.ComicsRemoteDataSource
import com.fgdc.marvelcharacters.domain.model.ComicListDomain
import com.fgdc.marvelcharacters.domain.repository.ComicsRepository
import com.fgdc.marvelcharacters.utils.exception.ErrorHandler
import com.fgdc.marvelcharacters.utils.functional.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ComicsRepositoryImpl @Inject constructor(
    private val comicsRemoteDataSource: ComicsRemoteDataSource
) : ComicsRepository {
    override fun getComicById(id: Int): Flow<State<List<ComicListDomain>>> = flow {
        emit(comicsRemoteDataSource.getComicById(id))
    }.catch {
        it.printStackTrace()
        emit(State.Error(Throwable(ErrorHandler.UNKNOWN_ERROR)))
    }
}
