package com.fgdc.marvelcharacters.di.module

import com.fgdc.marvelcharacters.data.datasource.remote.services.CharactersApi
import com.fgdc.marvelcharacters.data.datasource.remote.services.ComicsApi
import com.fgdc.marvelcharacters.data.datasource.remote.services.SeriesApi
import com.fgdc.marvelcharacters.data.repositories.*
import com.fgdc.marvelcharacters.utils.helpers.NetworkHandler
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    fun provideCharactersRepository(
        characterApi: CharactersApi,
        networkHandler: NetworkHandler
    ): CharactersRepository = CharactersRepositoryImpl(characterApi, networkHandler)

    @Provides
    fun provideComicsRepository(
        comicsApi: ComicsApi,
        networkHandler: NetworkHandler
    ): ComicsRepository = ComicsRepositoryImpl(comicsApi, networkHandler)

    @Provides
    fun provideSeriesRepository(
        seriesApi: SeriesApi,
        networkHandler: NetworkHandler
    ): SeriesRepository = SeriesRepositoryImpl(seriesApi, networkHandler)
}
