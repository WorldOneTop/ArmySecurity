package com.example.armysecurity.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.armysecurity.data.Cemetery
import com.example.armysecurity.data.Relics
import com.example.armysecurity.data.War
import com.example.armysecurity.data.WarMan
import com.example.armysecurity.db.DBDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class WarVM(private val dao: DBDao) : ViewModel() {

    fun getRelics(f:String, i:String):Flow<PagingData<Relics>> = Pager(
        config = PagingConfig(
            pageSize = 30,
            enablePlaceholders = false,
            maxSize = 100
        )
    ){
        if(i.isBlank())
            dao.selectRelics()
        else {
            when (f) {
                "명칭" -> dao.selectRelicsTtl(cnv(i))
                "국적" -> dao.selectRelicsPlace(cnv(i))
                "입수처" -> dao.selectRelicsObtmPlc(cnv(i))
                "관련 사건" -> dao.selectRelicsRelafr(cnv(i))
                else -> dao.selectRelicsWrtmyn(cnv(i))
            }
        }
    }.flow.cachedIn(viewModelScope)

    fun getWar(f:String, i:String):Flow<PagingData<War>> = Pager(
        config = PagingConfig(
            pageSize = 30,
            enablePlaceholders = false,
            maxSize = 100
        )
    ){
        if(i.isBlank())
            dao.selectWar()
        else {
            when (f) {
                "전투 명" -> dao.selectWarName(cnv(i))
                "장소" -> dao.selectWarPlace(cnv(i))
                "지휘관" -> dao.selectWarOrder(cnv(i))
                else -> dao.selectWar()
            }
        }
    }.flow.cachedIn(viewModelScope)

    fun getWarMan(f:String, i:String):Flow<PagingData<WarMan>> = Pager(
        config = PagingConfig(
            pageSize = 30,
            enablePlaceholders = false,
            maxSize = 100
        )
    ){
        if(i.isBlank())
            dao.selectWarMan()
        else{
            when(f){
                "이름" -> dao.selectWarManName(cnv(i))
                "출생지" -> dao.selectWarManPlace(cnv(i))
                "계급" -> dao.selectWarManRank(cnv(i))
                "상훈 내역" -> dao.selectWarManAward(cnv(i))
                else -> dao.selectWarMan()
            }
        }
    }.flow.cachedIn(viewModelScope)


    private fun cnv(s:String) = "%$s%"

}
