package com.george.goodsexpirydatetracker.ui.main.fragments

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.george.goodsexpirydatetracker.models.Commodity
import com.george.goodsexpirydatetracker.repositories.GoodsRepository

class MainViewModel @ViewModelInject constructor(
    val goodsRepo: GoodsRepository
) : ViewModel() {

    val fake = mutableListOf(
        Commodity("first", "Food", 163546516874659L),
        Commodity("first", "Food", 163546516874659L),
        Commodity("first", "Food", 163546516874659L),
        Commodity("first", "Food", 163546516874659L),
        Commodity("first", "Food", 163546516874659L),
        Commodity("first", "Food", 163546516874659L),
        Commodity("first", "Food", 163546516874659L),
        Commodity("first", "Food", 163546516874659L),
        Commodity("first", "Food", 163546516874659L),
        Commodity("first", "Food", 163546516874659L),
        Commodity("first", "Food", 163546516874659L),
        Commodity("first", "Food", 163546516874659L),
        Commodity("first", "Food", 163546516874659L),
        Commodity("first", "Food", 163546516874659L),
        Commodity("first", "Food", 163546516874659L),
        Commodity("first", "Food", 163546516874659L),
        Commodity("first", "Food", 163546516874659L),
        Commodity("first", "Food", 163546516874659L),
    )

}