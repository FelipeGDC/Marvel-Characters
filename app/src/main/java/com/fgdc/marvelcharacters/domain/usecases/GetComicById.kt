package com.fgdc.marvelcharacters.domain.usecases

import com.fgdc.marvelcharacters.data.repositories.ComicsRepository
import com.fgdc.marvelcharacters.domain.model.ComicListDomain
import com.fgdc.marvelcharacters.utils.functional.State
import javax.inject.Inject

class GetComicById @Inject constructor(private val comicsRepository: ComicsRepository) :
    BaseUseCase<State<List<ComicListDomain>>, GetComicById.Params>() {
    override fun run(params: Params?) = comicsRepository.getComicById(params?.id ?: 0)

    class Params(var id: Int)
}
