package com.george.goodsexpirydatetracker.repositories

import com.george.goodsexpirydatetracker.network.BaseDataSource
import com.george.goodsexpirydatetracker.db.GoodsDao
import com.george.goodsexpirydatetracker.models.Commodity
import com.george.goodsexpirydatetracker.network.ApiClient.Companion.api
import java.util.*
import javax.inject.Inject

class GoodsRepository @Inject constructor(private val goodsDao: GoodsDao) : BaseDataSource() {

    suspend fun getAllItemsFromRemoteRepositories() = safeApiCall { api.getAllItemsFromRemoteRepositories() }

    suspend fun insertCommodity(commodity: Commodity) =  goodsDao.upsertCommodity(commodity)

    suspend fun deleteCommodity(commodity: Commodity) =  goodsDao.deleteCommodity(commodity)

    fun getGoodsSortedByDateDESC() =  goodsDao.getGoodsSortedByExpiryDateDescending()

    fun getAllValidGoodsSortedByExpiryDateDescending(currentDate:Long = Date().time) =
        goodsDao.getAllValidGoodsSortedByExpiryDateDescending(currentDate)

    fun getAllExpiredGoodsSortedByExpiryDateDescending(currentDate:Long = Date().time) =
        goodsDao.getAllExpiredGoodsSortedByExpiryDateDescending(currentDate)

    // never used
    // fun getGoodsSortedByDateASC() = goodsDao.getGoodsSortedByDateASC()

}