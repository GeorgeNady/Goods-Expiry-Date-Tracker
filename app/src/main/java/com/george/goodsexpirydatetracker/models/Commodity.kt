package com.george.goodsexpirydatetracker.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "commodity_table")
data class Commodity(
    @ColumnInfo(name = "name") var name: String? = "",
    @ColumnInfo(name = "category") var category: String? = "",
    @ColumnInfo(name = "expiryDate") var expiryDate: Long? = 0L
) : Serializable {
    @PrimaryKey(autoGenerate = true) var id: Int? = 0
}
