package com.example.myborutocomposepaging3app.data.repository

import androidx.paging.PagingData
import com.example.myborutocomposepaging3app.data.local.entity.HeroEntity
import com.example.myborutocomposepaging3app.domain.repository.DataStoreOperations
import com.example.myborutocomposepaging3app.domain.repository.LocalDataSource
import com.example.myborutocomposepaging3app.domain.repository.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val dataStoreOperations: DataStoreOperations
) {

    fun getAllHeroes(): Flow<PagingData<HeroEntity>> {
        return remoteDataSource.getAllHeroes()
    }

    fun searchHeroes(query: String): Flow<PagingData<HeroEntity>> {
        return remoteDataSource.searchHeroes(query = query)
    }

    suspend fun getSelectedHero(heroId: Int): HeroEntity {
        return localDataSource.getSelectedHero(heroId = heroId)
    }

    suspend fun saveOnBoardingState(completed: Boolean) {
        dataStoreOperations.saveOnBoardingState(completed = completed)
    }

    fun readOnBoardingState(): Flow<Boolean> {
        return dataStoreOperations.readOnBoardingState()
    }

}