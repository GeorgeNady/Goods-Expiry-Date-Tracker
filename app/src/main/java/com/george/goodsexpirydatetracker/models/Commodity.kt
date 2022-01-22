package com.george.goodsexpirydatetracker.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "commodity_table")
data class Commodity(
    @SerializedName("mId")
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = false)
    var id: Int? = 0,
    @SerializedName("name")
    @ColumnInfo(name = "name")
    var name: String? = "",
    @SerializedName("category")
    @ColumnInfo(name = "category")
    var category: String? = "",
    @SerializedName("expiryDate")
    @ColumnInfo(name = "expiryDate")
    var expiryDate: Long? = 0L
) : Serializable
