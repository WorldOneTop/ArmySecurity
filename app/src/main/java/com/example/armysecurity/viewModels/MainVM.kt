package com.example.armysecurity.viewModels

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import android.widget.ProgressBar
import com.example.armysecurity.api.GithubAPI
import com.example.armysecurity.api.MndAPI
import com.example.armysecurity.db.AppDB
import com.example.armysecurity.db.PreFrncManager
import kotlinx.coroutines.*

class MainVM:ViewModel() {
    val div = 5


    fun initDownload(db: AppDB, prefrnc:SharedPreferences, progressBar: ProgressDialog,to:Int = 0){
        progressBar.progress = 0
        progressBar.max = 100
        CoroutineScope(Dispatchers.IO).launch {
            if(to==0){
                initCemetery(db,prefrnc,progressBar)
                initRelics(db,prefrnc,progressBar)
                initSale(db,prefrnc,progressBar)
            }else if(to==1){
                withContext(Dispatchers.Main){
                    progressBar.progress = 50
                }
                initRelics(db,prefrnc,progressBar)
                initSale(db,prefrnc,progressBar)
            }else if(to==2){
                withContext(Dispatchers.Main){
                    progressBar.progress = 85
                }
                initSale(db,prefrnc,progressBar)
            }
            withContext(Dispatchers.Main){
                progressBar.dismiss()
            }
        }
    }
    private suspend fun initCemetery(db: AppDB, prefrnc:SharedPreferences, progressBar: ProgressDialog) = coroutineScope{
        downloadCemetery(db,progressBar)
        initPrefrncCemetery(prefrnc,progressBar)
    }
    private suspend fun initRelics(db: AppDB, prefrnc:SharedPreferences, progressBar: ProgressDialog) = coroutineScope{
        downloadRelics(db,progressBar)
        initPrefrncRelics(prefrnc,progressBar)
    }
    private suspend fun initSale(db: AppDB, prefrnc:SharedPreferences, progressBar: ProgressDialog) = coroutineScope{
        downloadSale(db,progressBar)
        initPrefrncSale(prefrnc,progressBar)
    }

    private suspend fun downloadCemetery(db:AppDB, progressBar: ProgressDialog) = coroutineScope {
        db.dbDao().deleteCemetery()
        for(i in 0..div){
            launch{
                val data =MndAPI.request.getData(MndAPI.TYPE.CEMETERY_1,(MndAPI.MAX_VALUE/div*i), MndAPI.MAX_VALUE/div*(i+1)-1)
                if(data.error == null){
                    db.dbDao().insertCemetery(
                        data.CEMETERY_1.row
                    )
                    withContext(Dispatchers.Main){
                        progressBar.progress += 45/div/2 // init 3개중에 하나, 5번반복, Cemetery 두개 중 하나
                    }
                }
            }
            launch{
                val data = MndAPI.request.getData(MndAPI.TYPE.CEMETERY_2,(MndAPI.MAX_VALUE/div*i)+1, MndAPI.MAX_VALUE/div*(i+1))
                if(data.error == null) {
                    val dataCemetery = List(data.CEMETERY_2.row.size){
                        data.CEMETERY_2.row[it].getChemeter()
                    }
                    db.dbDao().insertCemetery(
                        dataCemetery
                    )
                    withContext(Dispatchers.Main) {
                        progressBar.progress += 45 / div / 2
                    }
                }
            }
        }
    }
    private suspend fun downloadRelics(db:AppDB, progressBar: ProgressDialog) = coroutineScope{
        launch {
            db.dbDao().deleteRelics()
            db.dbDao().insertRelics(
                GithubAPI.request.getRelics().relics!!
            )
        }
        launch {
            for(i in 0..div){
                delay(350)
                withContext(Dispatchers.Main){
                    progressBar.progress += 30/div
                }
            }
        }
    }
    private suspend fun downloadSale(db:AppDB, progressBar: ProgressDialog) = coroutineScope{
        db.dbDao().insertSale(MndAPI.request.getData(MndAPI.TYPE.SALE, 1,65535).SALE.row)
        withContext(Dispatchers.Main){
            progressBar.progress += 10
        }
    }
    private suspend fun initPrefrncCemetery(prefrnc: SharedPreferences, progressBar: ProgressDialog){
        prefrnc.edit()
            .putInt(MndAPI.TYPE.CEMETERY_1, MndAPI.request.getData(MndAPI.TYPE.CEMETERY_1,1,1).CEMETERY_1.count)
            .putInt(MndAPI.TYPE.CEMETERY_2, MndAPI.request.getData(MndAPI.TYPE.CEMETERY_2,1,1).CEMETERY_2.count)
            .apply()

        progressBar.progress += 5
    }
    private suspend fun initPrefrncRelics(prefrnc: SharedPreferences, progressBar: ProgressDialog){
        prefrnc.edit()
            .putInt("relicsCount", GithubAPI.request.getVersion().relicsCount!!)
            .apply()

        progressBar.progress += 5
    }
    private suspend fun initPrefrncSale(prefrnc: SharedPreferences, progressBar: ProgressDialog){
        prefrnc.edit()
            .putInt(MndAPI.TYPE.SALE, MndAPI.request.getData(MndAPI.TYPE.SALE,1,1).SALE.count)
            .apply()

        progressBar.progress += 5
    }

}