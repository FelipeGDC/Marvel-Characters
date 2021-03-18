package com.fgdc.marvelcharacters.domain.usecases

import com.fgdc.marvelcharacters.data.repositories.CharactersRepository
import com.fgdc.marvelcharacters.domain.model.CharacterListDomain
import com.fgdc.marvelcharacters.utils.functional.State

class GetCharactersByName(private val charactersRepository: CharactersRepository) :
    BaseUseCase<State<List<CharacterListDomain>>, GetCharactersByName.Params>() {
    override fun run(params: Params?) = charactersRepository.getCharactersByName(params?.name ?: "")

    class Params(var name: String)
}
