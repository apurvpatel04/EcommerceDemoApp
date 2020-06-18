package com.demo.ecommercedemoapp.ui

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.demo.ecommercedemoapp.R
import com.demo.ecommercedemoapp.adapter.ProductListAdapter
import com.demo.ecommercedemoapp.data.datamodels.ProductDetail
import com.demo.ecommercedemoapp.data.datamodels.Status
import com.demo.ecommercedemoapp.utils.hideProgressBar
import com.demo.ecommercedemoapp.utils.loadProgressBar
import com.demo.ecommercedemoapp.viewmodels.ProductListModel
import kotlinx.android.synthetic.main.activity_main.*


class ProductListActivity : AppCompatActivity() {

    lateinit var productListModel: ProductListModel
    var productListAdapter: ProductListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        productListModel = ViewModelProvider(this).get(ProductListModel::class.java)

        val authToken = intent.getStringExtra("authToken") ?: ""
        productListModel.authToken = authToken

        productListModel.getAllProducts().observe(this, Observer { response ->
            when (response.status) {
                Status.SUCCESS -> {
                    productPagePB.hideProgressBar()
                    if (response.data?.isNotEmpty() ?: false) {
                        val productList = ArrayList<ProductDetail>()
                        productList.addAll(response.data!!)
                        if (productListAdapter == null) {
                            productListAdapter = ProductListAdapter(productList)
                            productReccylerView.adapter = productListAdapter
                        } else {
                            productListAdapter?.productList = productList
                            productListAdapter?.notifyDataSetChanged()
                        }

                    }
                }
                Status.ERROR -> {
                    productPagePB.hideProgressBar()
                }
                Status.LOADING -> {
                    productPagePB.loadProgressBar()
                }
            }


        })

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.product_page_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_grid_list -> {
                val isGrid = productListModel.changeProductListing()
                productListAdapter?.isGrid = isGrid
                if (isGrid) item.switchListType(this,true,::switchToGridView) else item.switchListType(this,false,::switchToListView)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun switchToGridView() {
        productReccylerView.layoutManager = GridLayoutManager(this, 2)
        productReccylerView.adapter = productListAdapter
    }

    fun switchToListView() {
        productReccylerView.layoutManager = LinearLayoutManager(this)
        productReccylerView.adapter = productListAdapter
    }

    fun MenuItem.switchListType(context: Context, isGrid: Boolean, switchFun: () -> Unit) {
        isEnabled = false
        setIcon(
            ContextCompat.getDrawable(
                context,
                if (isGrid) R.drawable.ic_baseline_view_list_24 else R.drawable.ic_baseline_view_module_24
            )
        )
        switchFun()
        isEnabled = true

    }
}