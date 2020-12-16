package com.fgdc.marvelcharacters.data.repositories

import com.fgdc.marvelcharacters.domain.model.SeriesListDomain
import com.fgdc.marvelcharacters.utils.functional.State
import kotlinx.coroutines.flow.Flow

interface SeriesRepository {

    fun getSeriesById(id: Int): Flow<State<List<SeriesListDomain>>>

}