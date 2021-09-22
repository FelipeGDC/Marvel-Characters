package com.fgdc.marvelcharacters.domain.usecases

import com.fgdc.marvelcharacters.domain.repository.CharactersRepository
import com.fgdc.marvelcharacters.domain.model.CharacterListDomain
import com.fgdc.marvelcharacters.utils.functional.State
import javax.inject.Inject

class GetAllCharacters @Inject constructor(private val charactersRepository: CharactersRepository) :
    BaseUseCase<State<List<CharacterListDomain>>, GetAllCharacters.Params>() {
    override fun run(params: Params?) = charactersRepository.getAllCharacters(params?.offset ?: 0)

    class Params(var offset: Int)
}
