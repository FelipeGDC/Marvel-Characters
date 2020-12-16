package com.fgdc.marvelcharacters.di.module

import android.content.Context
import com.fgdc.marvelcharacters.BuildConfig
import com.fgdc.marvelcharacters.data.datasource.remote.services.*
import com.fgdc.marvelcharacters.utils.helpers.NetworkHandler
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    fun provideCharactersService(retrofit: Retrofit): CharactersApi = CharactersService(retrofit)

    @Provides
    fun provideComicsService(retrofit: Retrofit): ComicsApi = ComicsService(retrofit)

    @Provides
    fun provideSeriesService(retrofit: Retrofit): SeriesApi = SeriesService(retrofit)

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val clientBuilder = OkHttpClient.Builder()
            .followRedirects(false)

        clientBuilder.addInterceptor { chain ->
            val original = chain.request()
            val originalHttpUrl = original.url

            val apiKey = BuildConfig.MARVEL_API_PUBLIC_KEY
            val hash = BuildConfig.MARVEL_API_HASH

            val url = originalHttpUrl.newBuilder()
                .addQueryParameter("ts", "MarvelApp")
                .addQueryParameter("apikey", apiKey)
                .addQueryParameter("hash", hash)
                .build()

            val requestBuilder = original.newBuilder().url(url)

            chain.proceed(requestBuilder.build())
        }

        return clientBuilder.build()
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

    @Provides
    fun providesNetworkHandler(context: Context): NetworkHandler = NetworkHandler(context)
}
