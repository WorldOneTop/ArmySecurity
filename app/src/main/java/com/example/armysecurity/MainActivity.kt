package com.example.armysecurity

import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.armysecurity.api.MndAPI
import com.example.armysecurity.databinding.ActivityMainBinding
import com.example.armysecurity.db.AppDB
import com.example.armysecurity.db.PreFrncManager
import com.example.armysecurity.viewModels.MainVM

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val viewModel: MainVM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initToolbar()
        initNavigation()
        initDownload()
    }

    private fun initToolbar(){
        binding.mainToolbarBack.visibility = View.GONE
        binding.mainToolbarTitle.text = getString(R.string.tab_btm_myPage)
        binding.mainToolbarBack.setOnClickListener{
            onBackPressed()
        }
    }

    private fun initNavigation() {
        navController = (supportFragmentManager.findFragmentById(R.id.mainFragment)
                as NavHostFragment).navController

        binding.navBottom.setupWithNavController(navController)

        binding.navBottom.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.tab_btm_myPage -> binding.mainToolbarTitle.text =
                    getString(R.string.tab_btm_myPage)
                R.id.tab_btm_event -> binding.mainToolbarTitle.text =
                    getString(R.string.tab_btm_event)
                R.id.tab_btm_search -> binding.mainToolbarTitle.text =
                    getString(R.string.tab_btm_search)
            }
            NavigationUI.onNavDestinationSelected(item, navController)
            return@setOnItemSelectedListener true
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id == R.id.tab_btm_myPage
                || destination.id == R.id.tab_btm_event
                || destination.id == R.id.tab_btm_search){
                binding.mainToolbarBack.visibility = View.GONE
            }else{
                binding.mainToolbarBack.visibility = View.VISIBLE
            }
        }
    }

    private fun initDownload(){
        val progressBar = ProgressDialog(this)
        progressBar.setCancelable(false)
        progressBar.setMessage(getString(R.string.guide_init_download))
        progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)

        if(PreFrncManager.getInt(applicationContext,MndAPI.TYPE.CEMETERY_1) == PreFrncManager.DEFAULT_VALUE_INT
            || PreFrncManager.getInt(applicationContext,MndAPI.TYPE.CEMETERY_2) == PreFrncManager.DEFAULT_VALUE_INT){
            progressBar.show()
            viewModel.initDownload(AppDB.getInstance(applicationContext),PreFrncManager.getPreferences(applicationContext),progressBar)
        }else if(false){

        }
    }

    override fun onBackPressed() {
        if(navController.backQueue.size >= 4) {
            navController.navigateUp()
            if(navController.backQueue.size < 4)
                binding.mainToolbarBack.visibility = View.GONE
        }
        else
            super.onBackPressed()
    }

}