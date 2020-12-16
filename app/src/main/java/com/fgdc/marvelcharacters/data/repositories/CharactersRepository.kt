package com.fgdc.marvelcharacters.data.repositories

import com.fgdc.marvelcharacters.domain.model.CharacterDetailDomain
import com.fgdc.marvelcharacters.domain.model.CharacterListDomain
import com.fgdc.marvelcharacters.utils.functional.State
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {

    fun getAllCharacters(offset: Int): Flow<State<List<CharacterListDomain>>>

    fun getCharacterByName(name: String): Flow<State<List<CharacterListDomain>>>

    fun getCharacterById(id: Int): Flow<State<List<CharacterDetailDomain>>>

}