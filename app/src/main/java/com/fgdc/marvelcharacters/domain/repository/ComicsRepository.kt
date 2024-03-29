package com.fgdc.marvelcharacters.domain.repository

import com.fgdc.marvelcharacters.domain.model.ComicListDomain
import com.fgdc.marvelcharacters.utils.functional.State
import kotlinx.coroutines.flow.Flow

interface ComicsRepository {
    fun getComicById(id: Int): Flow<State<List<ComicListDomain>>>
}
