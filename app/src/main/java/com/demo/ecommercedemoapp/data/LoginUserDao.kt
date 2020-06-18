package com.demo.ecommercedemoapp.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.demo.ecommercedemoapp.data.datamodels.LoginUser

@Dao
interface LoginUserDao {

    @Query("SELECT * from user_detail")
   suspend fun getUserDetail(): List<LoginUser>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: LoginUser)
}