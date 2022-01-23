package com.george.goodsexpirydatetracker.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.george.goodsexpirydatetracker.models.Commodity

@Dao
interface GoodsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = Commodity::class)
    suspend fun upsertCommodity(commodity: Commodity): Long?

    @Delete
    suspend fun deleteCommodity(commodity: Commodity)

    @Query("SELECT * FROM commodity_table ORDER BY expiryDate DESC")
    fun getGoodsSortedByExpiryDateDescending(): LiveData<List<Commodity>>

    @Query("SELECT * FROM commodity_table WHERE expiryDate > :currentDate ORDER BY expiryDate DESC")
    fun getAllValidGoodsSortedByExpiryDateDescending(currentDate:Long) : LiveData<List<Commodity>>

    @Query("SELECT * FROM commodity_table WHERE expiryDate < :currentDate ORDER BY expiryDate DESC")
    fun getAllExpiredGoodsSortedByExpiryDateDescending(currentDate:Long) : LiveData<List<Commodity>>

    /**
     * # never used
     * */
    // @Query("SELECT * FROM commodity_table ORDER BY expiryDate ASC")
    // fun getGoodsSortedByDateASC(): LiveData<List<Commodity>>

}