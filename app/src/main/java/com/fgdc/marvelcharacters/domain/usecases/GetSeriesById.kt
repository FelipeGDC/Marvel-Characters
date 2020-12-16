package com.fgdc.marvelcharacters.domain.usecases

import com.fgdc.marvelcharacters.data.repositories.SeriesRepository
import com.fgdc.marvelcharacters.domain.model.SeriesListDomain
import com.fgdc.marvelcharacters.utils.functional.State

class GetSeriesById(private val seriesRepository: SeriesRepository) :
    BaseUseCase<State<List<SeriesListDomain>>, GetSeriesById.Params>() {
    override fun run(params: Params?) = seriesRepository.getSeriesById(params?.id ?: 0)

    class Params(var id: Int)
}
