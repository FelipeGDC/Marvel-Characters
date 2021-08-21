package com.fgdc.marvelcharacters.di.module

import com.fgdc.marvelcharacters.BuildConfig
import com.fgdc.marvelcharacters.data.datasource.remote.services.*
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
    fun provideCharactersService(retrofit: Retrofit): CharactersApi = CharactersService(retrofit)

    @Provides
    fun provideComicsService(retrofit: Retrofit): ComicsApi = ComicsService(retrofit)

    @Provides
    fun provideSeriesService(retrofit: Retrofit): SeriesApi = SeriesService(retrofit)

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
