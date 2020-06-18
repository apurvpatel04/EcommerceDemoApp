package com.demo.ecommercedemoapp.utils

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.demo.ecommercedemoapp.R

@BindingAdapter("imageUrl")
fun loadImage(imageView: ImageView, imageUrl:String){
    Glide
        .with(imageView.context)
        .load(imageUrl)
        .placeholder(R.drawable.ic_launcher_background)
        .into(imageView);
}

@BindingAdapter("drawableID")
fun loadLocalImage(imageView: ImageView, drawableID:Int){
    Glide
        .with(imageView.context)
        .load(drawableID)
        .placeholder(R.drawable.ic_launcher_background)
        .into(imageView);
}

@BindingAdapter("android:visibility")
fun setVisibility(view: View, value: Boolean) {
    view.visibility = if (value) View.VISIBLE else View.GONE
}

fun ProgressBar.loadProgressBar(){
    visibility = View.VISIBLE
    isIndeterminate = true
}
fun ProgressBar.hideProgressBar(){
    visibility = View.GONE
    isIndeterminate = false
}
