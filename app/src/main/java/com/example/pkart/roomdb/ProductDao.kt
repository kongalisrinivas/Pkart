package com.example.pkart.roomdb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ProductDao {
    @Insert
    suspend fun insertProduct(productModul: ProductModul)

    @Delete
    suspend fun deleteProduct(productModul: ProductModul)

    @Query("SELECT * FROM products")
     fun getAllProducts(): LiveData<List<ProductModul>>

    @Query("SELECT * FROM products WHERE  productId =:id")
    fun isExit(id: String): ProductModul
}