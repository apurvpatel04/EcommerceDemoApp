package com.demo.ecommercedemoapp.data.datamodels

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_detail")
data class LoginUser(@PrimaryKey val user_id:String, val quoteId:Int, val customer_token:String, val message:String, val email:String, val firstname:String, val lastname:String, val website_id:Int, val gender:String, val cart_item_count:Int, val referral_url:String, val address:String?, val swell_points:Int)