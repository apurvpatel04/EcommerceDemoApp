package com.demo.ecommercedemoapp.data.datamodels

import androidx.room.Entity
import androidx.room.PrimaryKey

data class ProductApiRes(val products:ArrayList<ProductDetail>)

@Entity(tableName = "product_detail")
data class ProductDetail(@PrimaryKey val product_id:String, val name:String, val sku:String, val url:String, val image:String, val price:Double, val new_item:Boolean, val flash_sale:Boolean, val best_seller:Boolean, val free_shipping:Boolean, val added_to_wishlist:Boolean, val type_id:String, val out_of_stock:Boolean, val rating:Int)
