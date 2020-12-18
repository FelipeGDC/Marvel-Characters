package com.fgdc.marvelcharacters.data.repositories

import com.fgdc.marvelcharacters.data.datasource.remote.services.CharactersApi
import com.fgdc.marvelcharacters.utils.exception.ErrorHandler
import com.fgdc.marvelcharacters.utils.exception.ErrorHandler.NETWORK_ERROR_MESSAGE
import com.fgdc.marvelcharacters.utils.functional.BadRequest
import com.fgdc.marvelcharacters.utils.functional.Error
import com.fgdc.marvelcharacters.utils.functional.ErrorNoConnection
import com.fgdc.marvelcharacters.utils.functional.Success
import com.fgdc.marvelcharacters.utils.helpers.NetworkHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CharactersRepositoryImpl @Inject constructor(
    private val apiService: CharactersApi,
    private val networkHandler: NetworkHandler
) : CharactersRepository {
    override fun getAllCharacters(offset: Int) = flow {
        emit(
            if (networkHandler.isConnected == true) {
                apiService.getAllCharacters(offset).run {
                    if (isSuccessful && body() != null) {
                        Success(body()!!.apiData.results.map { it.toCharacterListDomain() })
                    } else {
                        BadRequest(Throwable(ErrorHandler.BAD_REQUEST))
                    }
                }
            } else {
                ErrorNoConnection(Throwable(NETWORK_ERROR_MESSAGE))
            }
        )
    }.catch {
        it.printStackTrace()
        emit(Error(Throwable(ErrorHandler.UNKNOWN_ERROR)))
    }.flowOn(Dispatchers.IO)

    override fun getCharacterById(id: Int) = flow {
        emit(
            if (networkHandler.isConnected == true) {
                apiService.getCharacterById(id).run {
                    if (isSuccessful && body() != null) {
                        Success(body()!!.apiData.results.map { it.toCharacterDetailDomain() })
                    } else {
                        BadRequest(Throwable(ErrorHandler.BAD_REQUEST))
                    }
                }
            } else {
                ErrorNoConnection(Throwable(NETWORK_ERROR_MESSAGE))
            }
        )
    }.catch {
        it.printStackTrace()
        emit(Error(Throwable(ErrorHandler.UNKNOWN_ERROR)))
    }.flowOn(Dispatchers.IO)
}
