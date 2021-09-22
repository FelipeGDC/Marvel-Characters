package com.fgdc.marvelcharacters.domain.repository

import com.fgdc.marvelcharacters.domain.model.SeriesListDomain
import com.fgdc.marvelcharacters.utils.functional.State
import kotlinx.coroutines.flow.Flow

interface SeriesRepository {
    fun getSeriesById(id: Int): Flow<State<List<SeriesListDomain>>>
}
