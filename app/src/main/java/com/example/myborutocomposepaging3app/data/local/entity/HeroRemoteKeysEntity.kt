package com.example.myborutocomposepaging3app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myborutocomposepaging3app.util.Constant.HERO_REMOTE_KEYS_DATABASE_TABLE
import kotlinx.serialization.Serializable


@Serializable
@Entity(tableName = HERO_REMOTE_KEYS_DATABASE_TABLE)
data class HeroRemoteKeysEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int?,

    val prevPage: Int?,

    val nextPage: Int?,

    val lastUpdated: Long?
)