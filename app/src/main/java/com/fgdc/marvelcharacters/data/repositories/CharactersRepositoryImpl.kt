package com.fgdc.marvelcharacters.data.repositories

import com.fgdc.marvelcharacters.data.datasource.remote.services.CharactersApi
import com.fgdc.marvelcharacters.domain.model.CharacterListDomain
import com.fgdc.marvelcharacters.utils.exception.ErrorHandler.NETWORK_ERROR_MESSAGE
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
                        Error(Throwable("s"))
                    }
                }
            } else {
                ErrorNoConnection(Throwable(NETWORK_ERROR_MESSAGE))
            }
        )
    }.catch {
        it.printStackTrace()
        emit(Error(Throwable("s")))
    }.flowOn(Dispatchers.IO)

    override fun getCharacterByName(name: String): Flow<State<List<CharacterListDomain>>> {
        TODO("Not yet implemented")
    }

    override fun getCharacterById(id: Int) = flow {
        emit(
            if (networkHandler.isConnected == true) {
                apiService.getCharacterById(id).run {
                    if (isSuccessful && body() != null) {
                        Success(body()!!.apiData.results.map { it.toCharacterDetailDomain() })
                    } else {
                        Error(Throwable("s"))
                    }
                }
            } else {
                ErrorNoConnection(Throwable(NETWORK_ERROR_MESSAGE))
            }
        )
    }.catch {
        it.printStackTrace()
        emit(Error(Throwable("s")))
    }.flowOn(Dispatchers.IO)

}