package com.example.myborutocomposepaging3app.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myborutocomposepaging3app.data.local.entity.HeroEntity


@Dao
interface HeroDao {

    @Query("select * from hero_table order by id asc")
    fun getAllHeroes(): PagingSource<Int, HeroEntity>

    @Query("select * from hero_table where id = :heroId")
    fun getSelectedHero(heroId: Int): HeroEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addHeroes(heroes: List<HeroEntity>)

    @Query("delete from hero_table")
    suspend fun deleteAllHeroes()

}