package com.fgdc.marvelcharacters.data.datasource.series

import com.fgdc.marvelcharacters.domain.model.SeriesListDomain
import com.fgdc.marvelcharacters.utils.functional.State

interface SeriesRemoteDataSource {
    suspend fun getSeriesById(seriesId: Int): State<List<SeriesListDomain>>
}
