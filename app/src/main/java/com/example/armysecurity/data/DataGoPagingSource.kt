package com.example.armysecurity.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.armysecurity.api.DataRequest

class DataGoPagingSource(
    private val service: DataRequest,
    private val data: CemeteryDaejeon,
) : PagingSource<Int, CemeteryDaejeon>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CemeteryDaejeon> {
        val page = params.key ?: 1
        return try {
            val response = service.getData(
                page,data.name,data.deathday,data.qualification,data.burrday,data.sn,data.pname1,data.graveno
            )
            val maxNo = if(response.header.totalRows <= 50)
                1
            else if(response.header.totalRows % 50 == 0)
                response.header.totalRows / 50
            else
                response.header.totalRows / 50 + 1

            LoadResult.Page(
                data = response.body,
                prevKey = if(page == 1) null else page-1,
                nextKey = if(page == maxNo) null else page+1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CemeteryDaejeon>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }
}