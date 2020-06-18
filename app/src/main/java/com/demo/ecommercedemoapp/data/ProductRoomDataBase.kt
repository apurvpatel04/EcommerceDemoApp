package com.demo.ecommercedemoapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.demo.ecommercedemoapp.data.datamodels.LoginUser
import com.demo.ecommercedemoapp.data.datamodels.ProductDetail
import kotlinx.coroutines.CoroutineScope

@Database(entities = arrayOf(LoginUser::class,ProductDetail::class),version = 1)
public abstract class ProductRoomDataBase:RoomDatabase() {

    abstract fun loginUserDao(): LoginUserDao

    abstract fun productDetailDao(): ProductDetailDao

    companion object {

        private var INSTANCE: ProductRoomDataBase? = null

        fun getDatabase(context: Context): ProductRoomDataBase {

            if (INSTANCE != null) {
                return INSTANCE!!
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ProductRoomDataBase::class.java,
                    "product_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}