package com.demo.ecommercedemoapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.demo.ecommercedemoapp.data.datamodels.ProductDetail
import com.demo.ecommercedemoapp.databinding.ItemProductDetailGridBinding
import com.demo.ecommercedemoapp.databinding.ItemProductDetailListBinding
import com.demo.ecommercedemoapp.viewmodels.ProductItemViewModel

class ProductListAdapter(var productList: ArrayList<ProductDetail>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var isGrid = true
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        if (isGrid){
            val binding = ItemProductDetailGridBinding.inflate(inflater)
            return ProductGridViewHolder(binding)
        }else{
            val binding = ItemProductDetailListBinding.inflate(inflater)
            return ProductListViewHolder(binding)
        }

    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val productItem = productList.get(position)
        if (holder is ProductListViewHolder) {
            holder.bind(productItem)
        }else if ( holder is ProductGridViewHolder){
            holder.bind(productItem)
        }
    }

    internal inner class ProductListViewHolder(val binding: ItemProductDetailListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: ProductDetail) {
            binding.product = ProductItemViewModel(product)
        }
    }
    internal inner class ProductGridViewHolder(val binding: ItemProductDetailGridBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: ProductDetail) {
            binding.product = ProductItemViewModel(product)
        }
    }

}