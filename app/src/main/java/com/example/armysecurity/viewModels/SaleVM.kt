package com.example.armysecurity.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.armysecurity.data.Relics
import com.example.armysecurity.data.Sale
import com.example.armysecurity.db.DBDao
import kotlinx.coroutines.flow.Flow

class SaleVM (private val dao: DBDao) : ViewModel() {
    fun getData(f: String, i: String): Flow<PagingData<Sale>> = Pager(
        config = PagingConfig(
            pageSize = 30,
            enablePlaceholders = false,
            maxSize = 100
        )
    ) {
        if (i.isBlank())
            dao.selectSale()
        else {
            when (f) {
                "지역" -> dao.selectSaleRgn(cnv(i))
                "시설명" -> dao.selectSaleInst(cnv(i))
                else -> dao.selectSaleDcnt(cnv(i))
            }
        }
    }.flow.cachedIn(viewModelScope)

    private fun cnv(s:String) = "%$s%"
}