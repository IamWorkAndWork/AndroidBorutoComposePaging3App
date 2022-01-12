package com.example.myborutocomposepaging3app.di

import android.content.Context
import androidx.room.Room
import com.example.myborutocomposepaging3app.data.local.BorutoDatabase
import com.example.myborutocomposepaging3app.data.repository.LocalDataSourceImpl
import com.example.myborutocomposepaging3app.domain.repository.LocalDataSource
import com.example.myborutocomposepaging3app.util.Constant.BORUTO_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): BorutoDatabase {
        return Room.databaseBuilder(
            context,
            BorutoDatabase::class.java,
            BORUTO_DATABASE
        ).build()
    }

    @Provides
    @Singleton
    fun provideLocalDatabase(
        database: BorutoDatabase
    ): LocalDataSource {
        return LocalDataSourceImpl(
            borutoDatabase = database
        )
    }

}