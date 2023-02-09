package com.example.pkart.roomdb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.annotation.Nonnull

@Entity(tableName = "products")
data class ProductModul(
    @PrimaryKey
    @Nonnull
    val ProductId: String,
    @ColumnInfo(name = "productName")
    val productName: String,
    @ColumnInfo(name = "ProductImage")
    val productImage: String,
    @ColumnInfo(name = "ProductSp")
    val productSP: String
)