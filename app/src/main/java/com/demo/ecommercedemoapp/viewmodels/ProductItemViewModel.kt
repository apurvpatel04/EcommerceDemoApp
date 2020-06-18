package com.demo.ecommercedemoapp.viewmodels

import androidx.databinding.BaseObservable
import androidx.databinding.BindingAdapter
import com.demo.ecommercedemoapp.R
import com.demo.ecommercedemoapp.data.datamodels.ProductDetail

class ProductItemViewModel(val productDetail: ProductDetail) {

    fun getProductName(): String {
        return productDetail.name
    }

    fun getProductPrice(): String {
        return "$ ${productDetail.price}"
    }

    fun getProductRating(): String {
        return productDetail.rating.toString()
    }


    fun getImageUrl(): String {
        return productDetail.image ?: "";
    }


    fun isWishListed(): Int {
        val notWishlisted = R.drawable.ic_baseline_favorite_border_24
        val wishlisted = R.drawable.ic_baseline_favorite_24
        return  if (productDetail.added_to_wishlist) wishlisted else notWishlisted
    }
    fun isOutOfStock(): Boolean {
        return productDetail.out_of_stock
    }

}