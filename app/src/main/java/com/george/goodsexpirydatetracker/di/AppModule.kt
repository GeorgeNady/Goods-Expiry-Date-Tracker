package com.george.goodsexpirydatetracker.di

import android.content.Context
import androidx.room.Room
import com.george.goodsexpirydatetracker.db.GoodsDatabase
import com.george.goodsexpirydatetracker.utiles.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGoodsDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        GoodsDatabase::class.java,
        DATABASE_NAME
    ).build()

    @Provides
    @Singleton
    fun provideGoodsDao(
        db: GoodsDatabase
    ) = db.getDao()

}