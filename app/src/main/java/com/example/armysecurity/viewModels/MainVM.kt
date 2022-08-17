package com.example.armysecurity.viewModels

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import android.widget.ProgressBar
import com.example.armysecurity.api.MndAPI
import com.example.armysecurity.db.AppDB
import com.example.armysecurity.db.PreFrncManager
import kotlinx.coroutines.*

class MainVM:ViewModel() {


    fun initDownload(db: AppDB, prefrnc:SharedPreferences, progressBar: ProgressDialog,to:Int = 0){
        progressBar.progress = 0
        progressBar.max = 100
        CoroutineScope(Dispatchers.IO).launch {
            if(to==0){
                initCemetery(db,prefrnc,progressBar)


            }else if(to==1){
                withContext(Dispatchers.Main){
                    progressBar.progress = 33
                }


            }else if(to==2){
                withContext(Dispatchers.Main){
                    progressBar.progress = 66
                }

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

    private suspend fun downloadCemetery(db:AppDB, progressBar: ProgressDialog) = coroutineScope {
        db.dbDao().deleteCemetery()
        val div = 5
        for(i in 0..div){
            launch{
                val data =MndAPI.request.getCemetery1((MndAPI.MAX_VALUE/div*i), MndAPI.MAX_VALUE/div*(i+1)-1)
                if(data.error == null){
                    db.dbDao().insertCemetery(
                        data.CEMETERY_1.row
                    )
                    withContext(Dispatchers.Main){
                        progressBar.progress += 30/5/2 // init 3개중에 하나, 5번반복, Cemetery 두개 중 하나
                    }
                }
            }
            launch{
                val data = MndAPI.request.getCemetery2((MndAPI.MAX_VALUE/div*i)+1, MndAPI.MAX_VALUE/div*(i+1))
                if(data.error == null) {
                    val dataCemetery = List(data.CEMETERY_2.row.size){
                        data.CEMETERY_2.row[it].getChemeter()
                    }
                    db.dbDao().insertCemetery(
                        dataCemetery
                    )
                    withContext(Dispatchers.Main) {
                        progressBar.progress += 30 / 5 / 2
                    }
                }
            }
        }
    }
    private suspend fun initPrefrncCemetery(prefrnc: SharedPreferences, progressBar: ProgressDialog){
        prefrnc.edit()
            .putInt(MndAPI.TYPE.CEMETERY_1, MndAPI.request.getCemetery1(1,1).CEMETERY_1.count)
            .putInt(MndAPI.TYPE.CEMETERY_2, MndAPI.request.getCemetery2(1,1).CEMETERY_2.count)
            .apply()

        progressBar.progress += 3
    }

}