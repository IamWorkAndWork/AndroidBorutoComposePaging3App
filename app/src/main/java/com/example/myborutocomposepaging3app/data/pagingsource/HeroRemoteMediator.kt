package com.example.myborutocomposepaging3app.data.pagingsource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.myborutocomposepaging3app.data.local.BorutoDatabase
import com.example.myborutocomposepaging3app.data.local.entity.HeroEntity
import com.example.myborutocomposepaging3app.data.local.entity.HeroRemoteKeysEntity
import com.example.myborutocomposepaging3app.data.remote.BorutoApi
import javax.inject.Inject

@ExperimentalPagingApi
class HeroRemoteMediator @Inject constructor(
    private val borutoApi: BorutoApi,
    private val borutoDatabase: BorutoDatabase
) : RemoteMediator<Int, HeroEntity>() {

    private val heroDao = borutoDatabase.heroDao()
    private val heroRemoteKeysDao = borutoDatabase.heroRemoteKeysDao()

    override suspend fun initialize(): InitializeAction {
        val currentTime = System.currentTimeMillis()
        val lastUpdated = heroRemoteKeysDao.getRemoteKeys(heroId = 1)?.lastUpdated ?: 0L
        val cachTimeout = 1440

        val diffInMinutes = (currentTime - lastUpdated) / 1000 / 60
        return if (diffInMinutes.toInt() <= cachTimeout) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, HeroEntity>
    ): MediatorResult {
        return try {

            val page = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosetToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val isEndOfPagination = remoteKeys != null
                    val prevPage = remoteKeys?.prevPage ?: return MediatorResult.Success(
                        endOfPaginationReached = isEndOfPagination
                    )
                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val isEndOfPagination = remoteKeys != null
                    val nextPage = remoteKeys?.nextPage ?: return MediatorResult.Success(
                        endOfPaginationReached = isEndOfPagination
                    )
                    nextPage
                }
            }

            val response = borutoApi.getAllHeroes(page = page)

            if (response.heroes.isNotEmpty()) {
                borutoDatabase.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        heroDao.deleteAllHeroes()
                        heroRemoteKeysDao.deleteAllRemoteKeys()
                    }
                    val prevPage = response.prevPage
                    val nextPage = response.nextPage
                    val keys = response.heroes.map { hero ->
                        HeroRemoteKeysEntity(
                            id = hero.id,
                            prevPage = prevPage,
                            nextPage = nextPage,
                            lastUpdated = response.lastUpdated
                        )
                    }
                    heroRemoteKeysDao.addAllRemoteKeys(heroRemoteKeys = keys)
                    heroDao.addHeroes(heroes = response.heroes)
                }
            }

            MediatorResult.Success(
                endOfPaginationReached = response.nextPage == null
            )

        } catch (e: Exception) {

            MediatorResult.Error(e)

        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, HeroEntity>
    ): HeroRemoteKeysEntity? {
        return state.pages.firstOrNull() {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let { hero -> heroRemoteKeysDao.getRemoteKeys(heroId = hero.id) }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, HeroEntity>
    ): HeroRemoteKeysEntity? {
        return state.pages.lastOrNull() {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { hero ->
            heroRemoteKeysDao.getRemoteKeys(heroId = hero.id)
        }
    }

    private suspend fun getRemoteKeyClosetToCurrentPosition(
        state: PagingState<Int, HeroEntity>
    ): HeroRemoteKeysEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                heroRemoteKeysDao.getRemoteKeys(heroId = id)
            }
        }
    }

}