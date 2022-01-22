package com.george.goodsexpirydatetracker.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RepositoryResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("goods") val goods: List<Commodity>,
    @SerializedName("count") val count: Int
) : Serializable
