package com.example.armysecurity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.armysecurity.api.MndAPI
import com.example.armysecurity.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initToolbar()
        initNavigation()

    }

    private fun initToolbar(){
        setSupportActionBar(binding.mainToolbar)
    }

    private fun initNavigation() {
        navController = (supportFragmentManager.findFragmentById(R.id.mainFragment)
                as NavHostFragment).navController

        binding.navBottom.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.tab_btm_myPage -> supportActionBar?.title =
                    getString(R.string.tab_btm_myPage)
                R.id.tab_btm_event -> supportActionBar?.title =
                    getString(R.string.tab_btm_event)
                R.id.tab_btm_search -> supportActionBar?.title =
                    getString(R.string.tab_btm_search)
            }
        }
    }


}