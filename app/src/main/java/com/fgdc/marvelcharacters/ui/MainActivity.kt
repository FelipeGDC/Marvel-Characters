package com.fgdc.marvelcharacters.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import com.fgdc.marvelcharacters.R
import com.fgdc.marvelcharacters.databinding.ActivityMainBinding
import com.fgdc.marvelcharacters.di.component.ViewComponent
import com.fgdc.marvelcharacters.ui.base.BaseActivity

class MainActivity : BaseActivity() {

    override fun initializeInjector(viewComponent: ViewComponent) = viewComponent.inject(this)
    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.fragment)
        appBarConfiguration = AppBarConfiguration(navController.graph)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.toolbar.title = when (destination.id) {
                R.id.charactersList -> destination.label
                else -> ""
            }

            binding.toolbar.visibility = when (destination.id) {
                R.id.charactersList -> View.VISIBLE
                else -> View.GONE
            }
        }
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    fun showProgressStatus(viewStatus: Int) {
        binding.progress.visibility = viewStatus
    }
}
