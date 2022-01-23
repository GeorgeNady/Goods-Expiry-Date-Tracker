package com.george.goodsexpirydatetracker.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.george.goodsexpirydatetracker.models.Commodity
import com.george.goodsexpirydatetracker.models.RepositoryResponse
import com.george.goodsexpirydatetracker.repositories.GoodsRepository
import com.george.goodsexpirydatetracker.utiles.Resource
import kotlinx.coroutines.launch
import java.util.*

class MainViewModel @ViewModelInject constructor(
    private val repo: GoodsRepository
) : ViewModel() {

    private val _remoteDataSource = MutableLiveData<Resource<RepositoryResponse>>()
    val remoteDataSource: LiveData<Resource<RepositoryResponse>> get() = _remoteDataSource

    fun getAllItemsFromRemoteDataSource() = viewModelScope.launch {
        _remoteDataSource.value = Resource.loading(null)
        try {
            _remoteDataSource.value = repo.getAllItemsFromRemoteRepositories()
        } catch (e: Exception) {
            _remoteDataSource.value = Resource.failed(e.stackTraceToString())
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////// Database
    fun insertCommodity(commodity: Commodity) = viewModelScope.launch {
        repo.insertCommodity(commodity)
    }

    fun deleteCommodity(commodity: Commodity) = viewModelScope.launch {
        repo.deleteCommodity(commodity)
    }


    fun getAllGoodsDescending() = repo.getGoodsSortedByDateDESC()

    /**
     * # Returns Valid Goods from the local database
     * */
    fun getAllValidGoodsSortedByExpiryDateDescending() = repo.getAllValidGoodsSortedByExpiryDateDescending()
    /*fun allValidGoodsDescending(): LiveData<MutableList<Commodity>> {
        val data = getAllGoodsDescending().value
        val resultSet = MutableLiveData<MutableList<Commodity>>()
        val currentDate = Date().time
        data?.let { list ->
            list.forEach { c ->
                if ((c.expiryDate!! * 1000) > currentDate) resultSet.value!!.add(c)
            }
        }
        return resultSet
    }*/

    /**
     * # Returns Expired Goods from the local database
     * */
    fun getAllExpiredGoodsSortedByExpiryDateDescending() = repo.getAllExpiredGoodsSortedByExpiryDateDescending()
    /*fun allExpiredGoodsDescending(): LiveData<MutableList<Commodity>> {
        val data = getAllGoodsDescending().value
        val resultSet = MutableLiveData<MutableList<Commodity>>()
        val currentDate = Date().time
        data?.let { list ->
            list.forEach { c ->
                if ((c.expiryDate!! * 1000) < currentDate) resultSet.value!!.add(c)
            }
        }
        return resultSet
    }*/


}