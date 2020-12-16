package com.fgdc.marvelcharacters.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: Context) {

    @Provides
    @Singleton
    fun providesApplicationContext(): Context = application
}
