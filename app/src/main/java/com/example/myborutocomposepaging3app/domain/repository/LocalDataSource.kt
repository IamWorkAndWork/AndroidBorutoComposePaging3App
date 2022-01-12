package com.example.myborutocomposepaging3app.domain.repository

import com.example.myborutocomposepaging3app.data.local.entity.HeroEntity

interface LocalDataSource {
    suspend fun getSelectedHero(heroId: Int): HeroEntity
}