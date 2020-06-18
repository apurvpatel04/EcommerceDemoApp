package com.demo.ecommercedemoapp.data.repository

import com.demo.ecommercedemoapp.data.LoginUserDao
import com.demo.ecommercedemoapp.data.datamodels.LoginUser
import com.demo.ecommercedemoapp.data.networking.ApiInterface
import com.demo.ecommercedemoapp.data.networking.getNetworkService

class LoginRepository(val loginUserDao: LoginUserDao) {

    val apiInterface: ApiInterface


    init {
        apiInterface = getNetworkService()
    }

    suspend fun loginWithCredentials(email: String, password: String): LoginUser =
        apiInterface.loginApi(email, password)

    suspend fun insertLoggedInUser(loggedInUser: LoginUser) = loginUserDao.insertUser(loggedInUser)

   suspend  fun getLoggedInUser() = loginUserDao.getUserDetail()
}