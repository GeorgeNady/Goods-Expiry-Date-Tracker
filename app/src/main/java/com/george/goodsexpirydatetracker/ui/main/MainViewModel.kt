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

    val goodsList = mutableListOf<Commodity>()

    private val _goodsRepo = MutableLiveData<Resource<RepositoryResponse>>()
    val goodsRepo: LiveData<Resource<RepositoryResponse>> get() = _goodsRepo

    fun getAllItemsFromRemoteRepositories() = viewModelScope.launch {
        _goodsRepo.value = Resource.loading(null)
        try {
            _goodsRepo.value = repo.getAllItemsFromRemoteRepositories()
        } catch (e: Exception) {
            _goodsRepo.value = Resource.failed(e.stackTraceToString())
        }
    }


    private val _allGoodsDescending = MutableLiveData<Resource<List<Commodity>>>()
    val goodsDescending: LiveData<Resource<List<Commodity>>> get() = _allGoodsDescending

    fun getAllGoodsDescending()  = repo.getGoodsSortedByDateASC()

    fun insertCommodity(commodity: Commodity) : LiveData<Resource<Long>> {
        val liveData = MutableLiveData<Resource<Long>>()

        viewModelScope.launch {
            liveData.value = Resource.loading(null)
            try {
                liveData.value = Resource.success(repo.insertCommodity(commodity)!!)
            } catch (e:Exception) {
                liveData.value = Resource.failed(e.stackTraceToString())
            }
        }

        return liveData
    }

}