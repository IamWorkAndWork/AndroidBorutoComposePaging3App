package com.example.myborutocomposepaging3app.domain.repository

import androidx.paging.PagingData
import com.example.myborutocomposepaging3app.data.local.entity.HeroEntity
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {
    fun getAllHeroes(): Flow<PagingData<HeroEntity>>
    fun searchHeroes(query: String): Flow<PagingData<HeroEntity>>
}