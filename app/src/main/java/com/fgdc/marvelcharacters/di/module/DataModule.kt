package com.fgdc.marvelcharacters.di.module

import com.fgdc.marvelcharacters.data.datasource.characters.CharactersRemoteDataSource
import com.fgdc.marvelcharacters.data.datasource.comics.ComicsRemoteDataSource
import com.fgdc.marvelcharacters.data.datasource.series.SeriesRemoteDataSource
import com.fgdc.marvelcharacters.data.repositories.CharactersRepositoryImpl
import com.fgdc.marvelcharacters.data.repositories.ComicsRepositoryImpl
import com.fgdc.marvelcharacters.data.repositories.SeriesRepositoryImpl
import com.fgdc.marvelcharacters.domain.repository.CharactersRepository
import com.fgdc.marvelcharacters.domain.repository.ComicsRepository
import com.fgdc.marvelcharacters.domain.repository.SeriesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    fun provideCharactersRepository(
        charactersRemoteDataSource: CharactersRemoteDataSource
    ): CharactersRepository = CharactersRepositoryImpl(charactersRemoteDataSource)

    @Provides
    fun provideComicsRepository(
        comicsRemoteDataSource: ComicsRemoteDataSource
    ): ComicsRepository = ComicsRepositoryImpl(comicsRemoteDataSource)

    @Provides
    fun provideSeriesRepository(
        seriesRemoteDataSource: SeriesRemoteDataSource
    ): SeriesRepository = SeriesRepositoryImpl(seriesRemoteDataSource)
}
