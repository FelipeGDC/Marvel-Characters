package com.fgdc.marvelcharacters

import android.app.Application
import com.fgdc.marvelcharacters.di.component.ApplicationComponent
import com.fgdc.marvelcharacters.di.component.DaggerApplicationComponent
import com.fgdc.marvelcharacters.di.module.ApplicationModule

class MarvelApplication : Application() {

    val appComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        appComponent.inject(this)
    }
}
