package com.fgdc.marvelcharacters.data.repositories

import com.fgdc.marvelcharacters.data.datasource.characters.CharactersRemoteDataSource
import com.fgdc.marvelcharacters.domain.repository.CharactersRepository
import com.fgdc.marvelcharacters.utils.exception.ErrorHandler
import com.fgdc.marvelcharacters.utils.functional.State
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CharactersRepositoryImpl @Inject constructor(
    private val charactersRemoteDataSource: CharactersRemoteDataSource,
) : CharactersRepository {
    override fun getAllCharacters(offset: Int) = flow {
        emit(charactersRemoteDataSource.getAllCharacters(offset))
    }.catch {
        it.printStackTrace()
        emit(State.Error(Throwable(ErrorHandler.UNKNOWN_ERROR)))
    }

    override fun getCharacterById(id: Int) = flow {
        emit(charactersRemoteDataSource.getCharacterById(id))
    }.catch {
        it.printStackTrace()
        emit(State.Error(Throwable(ErrorHandler.UNKNOWN_ERROR)))
    }
}
