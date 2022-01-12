package com.example.myborutocomposepaging3app.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.myborutocomposepaging3app.data.local.BorutoDatabase
import com.example.myborutocomposepaging3app.data.local.entity.HeroEntity
import com.example.myborutocomposepaging3app.data.pagingsource.HeroRemoteMediator
import com.example.myborutocomposepaging3app.data.pagingsource.SearchHeroesSource
import com.example.myborutocomposepaging3app.data.remote.BorutoApi
import com.example.myborutocomposepaging3app.domain.repository.RemoteDataSource
import com.example.myborutocomposepaging3app.util.Constant.ITEMS_PER_PAGE
import kotlinx.coroutines.flow.Flow

@ExperimentalPagingApi
class RemoteDataSourceImpl(
    private val borutoApi: BorutoApi,
    private val borutoDatabase: BorutoDatabase
) : RemoteDataSource {

    private val heroDao = borutoDatabase.heroDao()

    override fun getAllHeroes(): Flow<PagingData<HeroEntity>> {
        val pagingSourceFactory = {
            heroDao.getAllHeroes()
        }
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = HeroRemoteMediator(
                borutoApi = borutoApi,
                borutoDatabase = borutoDatabase
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    override fun searchHeroes(query: String): Flow<PagingData<HeroEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = ITEMS_PER_PAGE
            ),
            pagingSourceFactory = {
                SearchHeroesSource(borutoApi = borutoApi, query = query)
            }
        ).flow
    }

}