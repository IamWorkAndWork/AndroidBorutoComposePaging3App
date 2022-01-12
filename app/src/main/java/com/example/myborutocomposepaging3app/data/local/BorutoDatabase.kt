package com.example.myborutocomposepaging3app.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.myborutocomposepaging3app.data.local.dao.HeroDao
import com.example.myborutocomposepaging3app.data.local.dao.HeroRemoteKeysDao
import com.example.myborutocomposepaging3app.data.local.entity.HeroEntity
import com.example.myborutocomposepaging3app.data.local.entity.HeroRemoteKeysEntity


@Database(entities = [HeroEntity::class, HeroRemoteKeysEntity::class], version = 1)
@TypeConverters(DatabaseConverter::class)
abstract class BorutoDatabase : RoomDatabase() {

    abstract fun heroDao(): HeroDao
    abstract fun heroRemoteKeysDao(): HeroRemoteKeysDao

    companion object {
        fun create(context: Context, useInMemory: Boolean): BorutoDatabase {
            val databaseBuilder = if (useInMemory) {
                Room.inMemoryDatabaseBuilder(context, BorutoDatabase::class.java)
            } else {
                Room.databaseBuilder(context, BorutoDatabase::class.java, "test_database.db")
            }
            return databaseBuilder.fallbackToDestructiveMigration()
                .build()
        }
    }

}