package com.fgdc.marvelcharacters.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fgdc.marvelcharacters.MarvelApplication
import com.fgdc.marvelcharacters.di.component.ViewComponent
import com.fgdc.marvelcharacters.di.module.ViewModule

abstract class BaseActivity : AppCompatActivity() {

    abstract fun initializeInjector(viewComponent: ViewComponent)

    private val activityComponent by lazy {
        (application as MarvelApplication)
            .appComponent
            .viewComponent(ViewModule(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeInjector(activityComponent)
    }
}
