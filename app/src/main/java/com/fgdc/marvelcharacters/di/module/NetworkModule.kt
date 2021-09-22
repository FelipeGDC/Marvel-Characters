package com.fgdc.marvelcharacters.di.module

import com.fgdc.marvelcharacters.BuildConfig
import com.fgdc.marvelcharacters.data.datasource.characters.CharactersRemoteDataSource
import com.fgdc.marvelcharacters.data.datasource.characters.CharactersRemoteDataSourceImpl
import com.fgdc.marvelcharacters.data.datasource.characters.api.CharactersApi
import com.fgdc.marvelcharacters.data.datasource.comics.ComicsRemoteDataSource
import com.fgdc.marvelcharacters.data.datasource.comics.ComicsRemoteDataSourceImpl
import com.fgdc.marvelcharacters.data.datasource.comics.api.ComicsApi
import com.fgdc.marvelcharacters.data.datasource.series.SeriesRemoteDataSource
import com.fgdc.marvelcharacters.data.datasource.series.SeriesRemoteDataSourceImpl
import com.fgdc.marvelcharacters.data.datasource.series.api.SeriesApi
import com.fgdc.marvelcharacters.utils.helpers.NetworkHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideCharactersRemoteDataSource(
        retrofit: Retrofit,
        networkHandler: NetworkHandler
    ): CharactersRemoteDataSource =
        CharactersRemoteDataSourceImpl(retrofit.create(CharactersApi::class.java), networkHandler)

    @Provides
    fun provideComicsRemoteDataSource(
        retrofit: Retrofit,
        networkHandler: NetworkHandler
    ): ComicsRemoteDataSource = ComicsRemoteDataSourceImpl(
        retrofit.create(ComicsApi::class.java), networkHandler
    )

    @Provides
    fun provideSeriesRemoteDataSource(
        retrofit: Retrofit,
        networkHandler: NetworkHandler
    ): SeriesRemoteDataSource = SeriesRemoteDataSourceImpl(
        retrofit.create(SeriesApi::class.java), networkHandler
    )

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val okHttpClient =
            OkHttpClient.Builder().addInterceptor(interceptor).followRedirects(false)

        okHttpClient.addInterceptor { chain ->
            val original = chain.request()
            val originalHttpUrl = original.url

            val apiKey = BuildConfig.MARVEL_API_PUBLIC_KEY
            val hash = BuildConfig.MARVEL_API_HASH
            val ts = BuildConfig.MARVEL_API_TS

            val url = originalHttpUrl.newBuilder()
                .addQueryParameter("ts", ts)
                .addQueryParameter("apikey", apiKey)
                .addQueryParameter("hash", hash)
                .build()

            val requestBuilder = original.newBuilder().url(url)

            chain.proceed(requestBuilder.build())
        }

        return okHttpClient.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create().withNullSerialization())
            .client(okHttpClient)
            .baseUrl(BuildConfig.MARVEL_API_BASE_URL)
            .build()
    }
}
