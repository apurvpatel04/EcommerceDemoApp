package com.demo.ecommercedemoapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.demo.ecommercedemoapp.data.datamodels.LoginUser
import com.demo.ecommercedemoapp.data.datamodels.ProductDetail

@Dao
interface ProductDetailDao {

    @Query("SELECT * from product_detail")
    suspend fun getProducts(): List<ProductDetail>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<ProductDetail>)
}