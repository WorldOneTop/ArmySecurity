package com.example.armysecurity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.armysecurity.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initNavBottom()
    }
    private fun initNavBottom(){
        binding.navBottom.setupWithNavController(
            (supportFragmentManager.findFragmentById(R.id.mainFragment) as NavHostFragment)
                .navController
        )

    }
}