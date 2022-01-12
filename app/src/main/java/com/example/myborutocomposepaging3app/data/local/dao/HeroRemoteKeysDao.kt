package com.example.myborutocomposepaging3app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myborutocomposepaging3app.data.local.entity.HeroEntity
import com.example.myborutocomposepaging3app.data.local.entity.HeroRemoteKeysEntity

@Dao
interface HeroRemoteKeysDao {

    @Query("select * from hero_remote_keys_table where id = :heroId")
    suspend fun getRemoteKeys(heroId: Int): HeroRemoteKeysEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(heroRemoteKeys: List<HeroRemoteKeysEntity>)

    @Query("delete from hero_remote_keys_table")
    suspend fun deleteAllRemoteKeys()

}