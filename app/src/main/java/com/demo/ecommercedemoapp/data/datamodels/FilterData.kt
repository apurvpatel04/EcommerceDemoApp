package com.demo.ecommercedemoapp.data.datamodels

import com.google.gson.annotations.SerializedName

data class FilterData(val filter:CategoryData)

data class CategoryData(val category_id:CategoryId)

class CategoryId{

    // in is defined in kotlin langauge so using SerializedName of GSON
    @SerializedName("in")
    val catId:Int = 808
}