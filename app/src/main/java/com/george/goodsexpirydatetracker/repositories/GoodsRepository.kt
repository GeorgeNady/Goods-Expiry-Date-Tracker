package com.george.goodsexpirydatetracker.repositories

import com.george.goodsexpirydatetracker.db.GoodsDao
import com.george.goodsexpirydatetracker.models.Commodity
import javax.inject.Inject

class GoodsRepository @Inject constructor(private val goodsDao: GoodsDao) {

    suspend fun insertCommodity(commodity: Commodity) = goodsDao.upsertCommodity(commodity)

    suspend fun deleteCommodity(commodity: Commodity) = goodsDao.deleteCommodity(commodity)

    fun getGoodsSortedByDateDESC() = goodsDao.getGoodsSortedByDateDESC()

    fun getGoodsSortedByDateASC() = goodsDao.getGoodsSortedByDateASC()

}