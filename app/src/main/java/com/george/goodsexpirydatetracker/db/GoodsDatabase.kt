package com.george.goodsexpirydatetracker.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.george.goodsexpirydatetracker.models.Commodity

@Database(entities = [Commodity::class], exportSchema = false, version = 1)
//@TypeConverters(value = arrayOf(DateTypeConverter::class))
abstract class GoodsDatabase : RoomDatabase() {

    abstract fun getDao(): GoodsDao

}