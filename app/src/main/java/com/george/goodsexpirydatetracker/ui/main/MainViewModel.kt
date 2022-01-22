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

class MainViewModel @ViewModelInject constructor(
    private val repo: GoodsRepository
) : ViewModel() {

     val allAvailableGoods = mutableListOf<Commodity>()

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

    fun getAllGoodsAscending()  = repo.getGoodsSortedByDateASC()

    fun getAllGoodsDescending()  = repo.getGoodsSortedByDateDESC()



}