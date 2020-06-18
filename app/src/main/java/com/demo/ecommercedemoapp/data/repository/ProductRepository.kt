package com.demo.ecommercedemoapp.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.demo.ecommercedemoapp.data.ProductDetailDao
import com.demo.ecommercedemoapp.data.datamodels.*
import com.demo.ecommercedemoapp.data.networking.ApiInterface
import com.demo.ecommercedemoapp.data.networking.getNetworkService

class ProductRepository(val productDetailDao: ProductDetailDao, authToken: String) {

    val apiInterface: ApiInterface
    private val TAG = ProductRepository::class.java.simpleName


    init {
        apiInterface = getNetworkService()
    }

    suspend fun getAllProducts(): LiveData<ServerResponse<List<ProductDetail>>> = liveData {


        try {
            emit(ServerResponse(Status.LOADING, null, "Please Wait"))
            val localData = getAllProductsFromDb()
            emit(ServerResponse(Status.SUCCESS, localData, "Data loaded from Db"))

        } catch (e: Exception) {
            Log.i(TAG, "getAllProductsFromDb >>" + e.localizedMessage)
        }

        try {
            val serverResponse = getAllProductsFromServer()
            emit(ServerResponse(Status.SUCCESS, serverResponse.products, "Data loaded from server"))
            productDetailDao.insertProducts(serverResponse.products)


        } catch (e: Exception) {
            emit(ServerResponse(Status.ERROR, null, "Error while fetching data"))
            Log.i(TAG, "getAllProductsFromServer >>" + e.localizedMessage)
        }


    }


    private suspend fun getAllProductsFromDb() = productDetailDao.getProducts()

    private suspend fun getAllProductsFromServer() =
        apiInterface.getAllProducts(FilterData(CategoryData(CategoryId())))
}