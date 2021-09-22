package com.fgdc.marvelcharacters.data.datasource.comics

import com.fgdc.marvelcharacters.data.datasource.comics.api.ComicsApi
import com.fgdc.marvelcharacters.domain.model.ComicListDomain
import com.fgdc.marvelcharacters.utils.exception.ErrorHandler
import com.fgdc.marvelcharacters.utils.functional.State
import com.fgdc.marvelcharacters.utils.helpers.NetworkHandler
import javax.inject.Inject

class ComicsRemoteDataSourceImpl @Inject constructor(
    private val comicsApi: ComicsApi,
    private val networkHandler: NetworkHandler
) : ComicsRemoteDataSource {

    override suspend fun getComicById(comicId: Int): State<List<ComicListDomain>> {
        if (!networkHandler.isInternetAvailable()) {
            return State.ErrorNoConnection(Throwable(ErrorHandler.NETWORK_ERROR_MESSAGE))
        }
        return comicsApi.getComicById(comicId).run {
            if (isSuccessful && body() != null) {
                State.Success(body()!!.apiData.results.map { it.toComicListDomain() })
            } else {
                State.BadRequest(Throwable(ErrorHandler.BAD_REQUEST))
            }
        }
    }
}
