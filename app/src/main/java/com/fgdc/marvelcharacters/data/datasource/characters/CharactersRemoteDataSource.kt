package com.fgdc.marvelcharacters.data.datasource.characters

import com.fgdc.marvelcharacters.domain.model.CharacterDetailDomain
import com.fgdc.marvelcharacters.domain.model.CharacterListDomain
import com.fgdc.marvelcharacters.utils.functional.State

interface CharactersRemoteDataSource {
    suspend fun getAllCharacters(offset: Int): State<List<CharacterListDomain>>
    suspend fun getCharacterById(characterId: Int): State<List<CharacterDetailDomain>>
}
