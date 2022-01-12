package com.example.myborutocomposepaging3app.domain.usecases

import com.example.myborutocomposepaging3app.data.local.entity.HeroEntity
import com.example.myborutocomposepaging3app.data.repository.Repository

class GetSelectedHeroUseCase(private val repository: Repository) {
    suspend operator fun invoke(heroId: Int): HeroEntity {
        return repository.getSelectedHero(heroId = heroId)
    }
}