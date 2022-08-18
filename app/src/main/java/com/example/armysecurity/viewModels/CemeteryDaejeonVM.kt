package com.example.armysecurity.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.armysecurity.api.DataAPI
import com.example.armysecurity.data.CemeteryDaejeon
import com.example.armysecurity.data.DataGoPagingSource
import kotlinx.coroutines.flow.Flow

class CemeteryDaejeonVM :ViewModel(){
    lateinit var data: Flow<PagingData<CemeteryDaejeon>>

    fun setData(cemetery:CemeteryDaejeon){
        data = Pager(
            config = PagingConfig(
                pageSize = 50,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { DataGoPagingSource(DataAPI.request, cemetery) }
        ).flow.cachedIn(viewModelScope)
    }

}