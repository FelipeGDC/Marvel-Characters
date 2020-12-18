package com.fgdc.marvelcharacters.ui.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.fgdc.marvelcharacters.MarvelApplication
import com.fgdc.marvelcharacters.di.component.ViewComponent
import com.fgdc.marvelcharacters.di.module.ViewModule
import com.fgdc.marvelcharacters.ui.MainActivity

abstract class BaseFragment : Fragment() {

    protected abstract fun initializeInjector(viewComponent: ViewComponent)

    private val fragmentComponent by lazy {
        (activity?.application as MarvelApplication)
            .appComponent
            .viewComponent(ViewModule(activity as BaseActivity))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeInjector(fragmentComponent)
    }

    protected fun showSpinner(show: Boolean?) {
        when (show) {
            true -> showProgress()
            false -> hideProgress()
        }
    }

    private fun showProgress() = progressStatus(View.VISIBLE)

    private fun hideProgress() = progressStatus(View.GONE)

    private fun progressStatus(viewStatus: Int) =
        with(activity) {
            if (this is MainActivity) {
                this.showProgressStatus(viewStatus)
            }
        }
}
