package com.example.armysecurity.viewModels

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import android.widget.ProgressBar
import androidx.lifecycle.MutableLiveData
import com.example.armysecurity.api.GithubAPI
import com.example.armysecurity.api.MndAPI
import com.example.armysecurity.data.MyData
import com.example.armysecurity.data.MyDataList
import com.example.armysecurity.db.AppDB
import com.example.armysecurity.db.PreFrncManager
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.*
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.lang.Exception

class MainVM:ViewModel() {
    val div = 5
    lateinit var myData: MyDataList
    var myLiveData: MutableLiveData<List<MyData>> = MutableLiveData<List<MyData>>()
    val FILE_NAME = "myData"

    fun initMyData(context:Context){
        myData = try{
            val string = context.openFileInput(FILE_NAME).bufferedReader().readText()
            Gson().fromJson((string), MyDataList::class.java)
        }catch (e:FileNotFoundException){
            MyDataList(ArrayList())
        }
        myLiveData.value = myData.list
    }

    fun addData(context: Context, data:MyData){
        myData.list.add(data)
        saveData(context)
    }
    fun removeData(context:Context, data:MyData){
        myData.list.remove(data)
        saveData(context)
    }
    fun saveData(context: Context){
        val string = Gson().toJson(myData)
        myLiveData.value = myData.list
        context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE).use {
            it.write(string.toByteArray())
        }
    }
    fun isBookmarke(data:MyData):String?{
        for(d in myData.list){
            if(d.isEqual(data))
                return d.memo
        }
        return null
    }


    fun initDownload(db: AppDB, prefrnc:SharedPreferences, progressBar: ProgressDialog,to:Int = 0){
        progressBar.progress = 0
        progressBar.max = 100
        CoroutineScope(Dispatchers.IO).launch {
            if(to==0){
                initCemetery(db,prefrnc,progressBar)
                initRelics(db,prefrnc,progressBar)
                initEtc(db,prefrnc,progressBar)
            }else if(to==1){
                withContext(Dispatchers.Main){
                    progressBar.progress = 40
                }
                initRelics(db,prefrnc,progressBar)
                initEtc(db,prefrnc,progressBar)
            }else if(to==2){
                withContext(Dispatchers.Main){
                    progressBar.progress = 70
                }
                initEtc(db,prefrnc,progressBar)
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
    private suspend fun initEtc(db: AppDB, prefrnc:SharedPreferences, progressBar: ProgressDialog) = coroutineScope{
        downloadEtc(db,progressBar, prefrnc)
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
                        progressBar.progress += 35/div/2 // init 3개중에 하나, 5번반복, Cemetery 두개 중 하나
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
                        progressBar.progress += 35 / div / 2
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
                    progressBar.progress += 25/div
                }
            }
        }
    }
    private suspend fun downloadEtc(db:AppDB, progressBar: ProgressDialog, prefrnc: SharedPreferences) = coroutineScope {
        launch { //sale
            db.dbDao().deleteSale()
            db.dbDao().insertSale(MndAPI.request.getData(MndAPI.TYPE.SALE, 1,65535).SALE.row)
            withContext(Dispatchers.Main){
                progressBar.progress += 8
            }
            initPrefrncSale(prefrnc,progressBar)
        }
        launch { //war
            db.dbDao().deleteWar()
            db.dbDao().insertWar(MndAPI.request.getData(MndAPI.TYPE.WAR, 1,65535).WAR.row)
            db.dbDao().insertWar(MndAPI.request.getData(MndAPI.TYPE.WAR_2, 1,65535).WAR_2.row)
            withContext(Dispatchers.Main){
                progressBar.progress += 8
            }
            initPrefrncWar(prefrnc,progressBar)
        }
        launch { //war man
            db.dbDao().deleteWarMan()
            db.dbDao().insertWarMan(MndAPI.request.getData(MndAPI.TYPE.WARMAN, 1,65535).WARMAN.row)
            db.dbDao().insertWarMan(MndAPI.request.getData(MndAPI.TYPE.WARMAN_2, 1,65535).WARMAN_2.row)
            withContext(Dispatchers.Main){
                progressBar.progress += 8
            }
            initPrefrncWarMan(prefrnc,progressBar)
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
        progressBar.progress += 2
    }
    private suspend fun initPrefrncWar(prefrnc: SharedPreferences, progressBar: ProgressDialog){
        prefrnc.edit()
            .putInt(MndAPI.TYPE.WAR, MndAPI.request.getData(MndAPI.TYPE.WAR,1,1).WAR.count)
            .putInt(MndAPI.TYPE.WAR_2, MndAPI.request.getData(MndAPI.TYPE.WAR_2,1,1).WAR_2.count)
            .apply()
        progressBar.progress += 2
    }
    private suspend fun initPrefrncWarMan(prefrnc: SharedPreferences, progressBar: ProgressDialog){
        prefrnc.edit()
            .putInt(MndAPI.TYPE.WARMAN, MndAPI.request.getData(MndAPI.TYPE.WARMAN,1,1).WARMAN.count)
            .putInt(MndAPI.TYPE.WARMAN_2, MndAPI.request.getData(MndAPI.TYPE.WARMAN_2,1,1).WARMAN_2.count)
            .apply()
        progressBar.progress += 2
    }

}