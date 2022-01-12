package com.example.myborutocomposepaging3app.domain.usecases

import androidx.paging.PagingData
import com.example.myborutocomposepaging3app.data.local.entity.HeroEntity
import com.example.myborutocomposepaging3app.data.repository.Repository
import kotlinx.coroutines.flow.Flow

class SearchHeroesUseCase(
    private val repository: Repository
) {
    operator fun invoke(query: String): Flow<PagingData<HeroEntity>> {
        return repository.searchHeroes(query = query)
    }
}