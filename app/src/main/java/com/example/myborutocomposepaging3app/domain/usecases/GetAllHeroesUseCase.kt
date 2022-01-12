package com.example.myborutocomposepaging3app.domain.usecases

import androidx.paging.PagingData
import com.example.myborutocomposepaging3app.data.local.entity.HeroEntity
import com.example.myborutocomposepaging3app.data.repository.Repository
import kotlinx.coroutines.flow.Flow

class GetAllHeroesUseCase(
    private val repository: Repository
) {
    operator fun invoke(): Flow<PagingData<HeroEntity>> {
        return repository.getAllHeroes()
    }
}