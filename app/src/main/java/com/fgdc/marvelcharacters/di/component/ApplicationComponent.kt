package com.fgdc.marvelcharacters.di.component

import com.fgdc.marvelcharacters.MarvelApplication
import com.fgdc.marvelcharacters.di.module.*
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [ApplicationModule::class, NetworkModule::class, DataModule::class, UseCaseModule::class]
)
interface ApplicationComponent {

    fun inject(application: MarvelApplication)

    fun viewComponent(viewModule: ViewModule): ViewComponent
}
