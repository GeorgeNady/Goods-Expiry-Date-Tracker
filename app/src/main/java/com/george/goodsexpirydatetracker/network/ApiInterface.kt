package com.george.goodsexpirydatetracker.network

import com.george.goodsexpirydatetracker.models.RepositoryResponse
import retrofit2.Response
import retrofit2.http.*

@Suppress("FunctionName")
interface ApiInterface {

    @GET("commidity")
    suspend fun getAllItemsFromRemoteRepositories(): Response<RepositoryResponse>

}