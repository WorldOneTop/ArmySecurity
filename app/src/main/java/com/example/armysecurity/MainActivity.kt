package com.example.armysecurity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.armysecurity.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var navController:NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initNavigation()
    }
    private fun initNavigation(){
        navController = (supportFragmentManager.findFragmentById(R.id.mainFragment)
                as NavHostFragment).navController

        binding.navBottom.setupWithNavController(navController)

        navController.addOnDestinationChangedListener{_, destination, _ ->
            when(destination.id){
                R.id.tab_btm_myPage -> binding.mainToolbarName.text = getString(R.string.tab_btm_myPage)
                R.id.tab_btm_event -> binding.mainToolbarName.text = getString(R.string.tab_btm_event)
                R.id.tab_btm_search -> binding.mainToolbarName.text = getString(R.string.tab_btm_search)
            }
        }
    }
}