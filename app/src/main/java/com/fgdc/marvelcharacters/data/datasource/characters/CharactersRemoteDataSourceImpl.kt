package com.fgdc.marvelcharacters.data.datasource.characters

import com.fgdc.marvelcharacters.data.datasource.characters.api.CharactersApi
import com.fgdc.marvelcharacters.domain.model.CharacterDetailDomain
import com.fgdc.marvelcharacters.domain.model.CharacterListDomain
import com.fgdc.marvelcharacters.utils.exception.ErrorHandler
import com.fgdc.marvelcharacters.utils.functional.State
import com.fgdc.marvelcharacters.utils.helpers.NetworkHandler
import javax.inject.Inject

class CharactersRemoteDataSourceImpl @Inject constructor(
    private val charactersApi: CharactersApi,
    private val networkHandler: NetworkHandler
) : CharactersRemoteDataSource {

    override suspend fun getAllCharacters(offset: Int): State<List<CharacterListDomain>> {
        if (!networkHandler.isInternetAvailable()) {
            return State.ErrorNoConnection(Throwable(ErrorHandler.NETWORK_ERROR_MESSAGE))
        }
        return charactersApi.getAllCharacters(offset).run {
            if (isSuccessful && body() != null) {
                State.Success(body()!!.apiData.results.map { it.toCharacterListDomain() })
            } else {
                State.BadRequest(Throwable(ErrorHandler.BAD_REQUEST))
            }
        }
    }

    override suspend fun getCharacterById(characterId: Int): State<List<CharacterDetailDomain>> {
        if (!networkHandler.isInternetAvailable()) {
            return State.ErrorNoConnection(Throwable(ErrorHandler.NETWORK_ERROR_MESSAGE))
        }
        return charactersApi.getCharacterById(characterId).run {
            if (isSuccessful && body() != null) {
                State.Success(body()!!.apiData.results.map { it.toCharacterDetailDomain() })
            } else {
                State.BadRequest(Throwable(ErrorHandler.BAD_REQUEST))
            }
        }
    }
}
