package com.fgdc.marvelcharacters.di.module

import com.fgdc.marvelcharacters.data.repositories.CharactersRepository
import com.fgdc.marvelcharacters.data.repositories.ComicsRepository
import com.fgdc.marvelcharacters.data.repositories.SeriesRepository
import com.fgdc.marvelcharacters.domain.usecases.GetAllCharacters
import com.fgdc.marvelcharacters.domain.usecases.GetCharacterById
import com.fgdc.marvelcharacters.domain.usecases.GetComicById
import com.fgdc.marvelcharacters.domain.usecases.GetSeriesById
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    fun provideGetAllCharactersUseCase(charactersRepository: CharactersRepository): GetAllCharacters =
        GetAllCharacters(charactersRepository)

    @Provides
    fun provideGetCharacterByIdUseCase(charactersRepository: CharactersRepository): GetCharacterById =
        GetCharacterById(charactersRepository)

    @Provides
    fun provideGetComicByIdUseCase(comicsRepository: ComicsRepository): GetComicById =
        GetComicById(comicsRepository)

    @Provides
    fun provideGetSeriesByIdUseCase(seriesRepository: SeriesRepository): GetSeriesById =
        GetSeriesById(seriesRepository)
}
