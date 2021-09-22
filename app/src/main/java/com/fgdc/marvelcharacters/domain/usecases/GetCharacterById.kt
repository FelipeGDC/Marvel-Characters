package com.fgdc.marvelcharacters.domain.usecases

import com.fgdc.marvelcharacters.domain.repository.CharactersRepository
import com.fgdc.marvelcharacters.domain.model.CharacterDetailDomain
import com.fgdc.marvelcharacters.utils.functional.State
import javax.inject.Inject

class GetCharacterById @Inject constructor(private val charactersRepository: CharactersRepository) :
    BaseUseCase<State<List<CharacterDetailDomain>>, GetCharacterById.Params>() {
    override fun run(params: Params?) = charactersRepository.getCharacterById(params?.id ?: 0)

    class Params(var id: Int)
}
