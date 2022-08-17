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
import com.example.armysecurity.db.DBDao
import kotlinx.coroutines.flow.Flow

class CemeteryVM( private val dao: DBDao) : ViewModel() {
    private var filter:String = ""
    private var input:String = ""
    var data: Flow<PagingData<Cemetery>> = Pager(
        config = PagingConfig(
            pageSize = 30,
            enablePlaceholders = false,
            maxSize = 100
        )
    ) {dao.selectCemetery()}.flow.cachedIn(viewModelScope)

    fun setFilter(index:String,v:String):Boolean{
        val value = injection(v)

        if(filter == index && input == v)
            return false
        if(input.isBlank() && v.isBlank())
            return false
        if(index.isBlank() && v.isNotBlank())
            return false

        filter = index
        input = v
        data = Pager(
                config = PagingConfig(
                    pageSize = 30,
                    enablePlaceholders = false,
                    maxSize = 100
                )
                ) {
            if(v == "%%"){
                dao.selectCemetery()
            }else{
                when(index){
                    "안장자 명"-> dao.selectCemeteryName(value)
                    "계급"-> dao.selectCemeteryRank(value)
                    "신분"-> dao.selectCemeteryIdentity(value)
                    "안장 일자"-> dao.selectCemeteryMoveDate(value)
                    "안장 위치"-> dao.selectCemeteryMovePlc(value)
                    "순국 일자"-> dao.selectCemeteryDeathDate(value)
                    "순국 장소"-> dao.selectCemeteryDeathPlc(value)
                    else-> dao.selectCemetery()
                }
            }
        }.flow.cachedIn(viewModelScope)
        return true
    }

    private fun injection(s:String):String{
        //TODO 인젝션 처리
        return "%$s%"
    }

}

class CemeteryVMFactory(private val dao: DBDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CemeteryVM(dao) as T
    }
}