package com.example.myborutocomposepaging3app.data.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.myborutocomposepaging3app.data.local.entity.HeroEntity
import com.example.myborutocomposepaging3app.data.remote.BorutoApi
import javax.inject.Inject

class SearchHeroesSource @Inject constructor(
    private val borutoApi: BorutoApi,
    private val query: String
) : PagingSource<Int, HeroEntity>() {

    override fun getRefreshKey(state: PagingState<Int, HeroEntity>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, HeroEntity> {

        return try {

            val apiResponse = borutoApi.searchHeroes(name = query)
            val heroes = apiResponse.heroes

            if (heroes.isNotEmpty()) {
                LoadResult.Page(
                    data = heroes,
                    prevKey = apiResponse.prevPage,
                    nextKey = apiResponse.nextPage
                )
            } else {
                LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            }

        } catch (e: Exception) {
            LoadResult.Error(e)
        }

    }

}