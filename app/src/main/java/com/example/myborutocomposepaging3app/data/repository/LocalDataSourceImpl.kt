package com.example.myborutocomposepaging3app.data.repository

import com.example.myborutocomposepaging3app.data.local.BorutoDatabase
import com.example.myborutocomposepaging3app.data.local.entity.HeroEntity
import com.example.myborutocomposepaging3app.domain.repository.LocalDataSource

class LocalDataSourceImpl(borutoDatabase: BorutoDatabase) : LocalDataSource {

    private val heroDao = borutoDatabase.heroDao()

    override suspend fun getSelectedHero(heroId: Int): HeroEntity {
        return heroDao.getSelectedHero(heroId = heroId)
    }

}