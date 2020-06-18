package com.demo.ecommercedemoapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.demo.ecommercedemoapp.data.ProductRoomDataBase
import com.demo.ecommercedemoapp.data.datamodels.ProductDetail
import com.demo.ecommercedemoapp.data.datamodels.ServerResponse
import com.demo.ecommercedemoapp.data.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductListModel(application: Application) : AndroidViewModel(application) {

    private val TAG = ProductListModel::class.java.simpleName
    private val productRepository: ProductRepository
    var authToken = ""
    var isGrid = true

    init {
        val productDetailDao = ProductRoomDataBase.getDatabase(application).productDetailDao()
        productRepository = ProductRepository(productDetailDao, authToken)
    }


    fun changeProductListing(): Boolean {
        isGrid = !isGrid
        return isGrid
    }



    fun getAllProducts(): LiveData<ServerResponse<List<ProductDetail>>> {
        var productList = MutableLiveData<ServerResponse<List<ProductDetail>>>()
        viewModelScope.launch{
            productList = productRepository.getAllProducts() as MutableLiveData<ServerResponse<List<ProductDetail>>>
        }

        return productList
    }


}