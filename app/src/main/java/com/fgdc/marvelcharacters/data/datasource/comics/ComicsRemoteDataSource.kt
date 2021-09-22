package com.fgdc.marvelcharacters.data.datasource.comics

import com.fgdc.marvelcharacters.domain.model.ComicListDomain
import com.fgdc.marvelcharacters.utils.functional.State

interface ComicsRemoteDataSource {
    suspend fun getComicById(comicId: Int): State<List<ComicListDomain>>
}
