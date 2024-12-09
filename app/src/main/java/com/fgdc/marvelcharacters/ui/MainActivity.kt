package com.fgdc.marvelcharacters.ui

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedDispatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import com.fgdc.marvelcharacters.R
import com.fgdc.marvelcharacters.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        val navController = navHostFragment.navController
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
        binding.toolbar.setNavigationOnClickListener { OnBackPressedDispatcher() }
    }

    fun showProgressStatus(viewStatus: Int) {
        binding.progress.visibility = viewStatus
    }
}
