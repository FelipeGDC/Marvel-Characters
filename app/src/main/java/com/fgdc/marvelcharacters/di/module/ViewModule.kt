package com.fgdc.marvelcharacters.di.module

import android.app.Activity
import com.fgdc.marvelcharacters.di.scope.ViewScope
import com.fgdc.marvelcharacters.ui.base.BaseActivity
import dagger.Module
import dagger.Provides

@Module
class ViewModule(private val activity: BaseActivity) {

    @Provides
    @ViewScope
    internal fun activity(): Activity {
        return activity
    }
}
