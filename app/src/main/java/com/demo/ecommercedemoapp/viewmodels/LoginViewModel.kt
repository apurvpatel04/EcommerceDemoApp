package com.demo.ecommercedemoapp.viewmodels

import android.app.Application
import android.util.Log
import android.util.Patterns
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.demo.ecommercedemoapp.R
import com.demo.ecommercedemoapp.data.ProductRoomDataBase
import com.demo.ecommercedemoapp.data.datamodels.LoginFormValidation
import com.demo.ecommercedemoapp.data.datamodels.LoginUser
import com.demo.ecommercedemoapp.data.datamodels.ServerResponse
import com.demo.ecommercedemoapp.data.datamodels.Status
import com.demo.ecommercedemoapp.data.repository.LoginRepository
import kotlinx.coroutines.launch


class LoginViewModel(application: Application) : AndroidViewModel(application) {
    val loginUser = MutableLiveData<ServerResponse<LoginUser>>()
    val loginFormValidation = MutableLiveData<LoginFormValidation>()

    private val TAG = LoginViewModel::class.java.simpleName
    private val loginRepository: LoginRepository

    init {
        val loginUserDao = ProductRoomDataBase.getDatabase(application).loginUserDao()
        loginRepository = LoginRepository(loginUserDao)
    }


    fun validateData(username: String, password: String) {
        if (!isUserNameValid(username)) {
            loginFormValidation.value = LoginFormValidation(usernameError = R.string.invalid_username_msg)
        } else if (!isPasswordValid(password)) {
            loginFormValidation.value = LoginFormValidation(passwordError = R.string.invalid_pswd_msg)
        } else {
            loginFormValidation.value = LoginFormValidation(isDataValid = true)
        }
    }


    fun loginWithCredentials(email: String, password: String) {
        viewModelScope.launch {
            try {

                loginUser.value = ServerResponse(Status.LOADING, null, "Please Wait")
                val response = loginRepository.loginWithCredentials(email, password)
                loginUser.value = ServerResponse(Status.SUCCESS, response, response.message)
                loginRepository.insertLoggedInUser(response)

            } catch (error: Exception) {
                loginUser.value = ServerResponse(Status.ERROR, null, "Login Failed")
            }
        }
    }


    fun checkUserLoggedIn(): LiveData<LoginUser> {
        val logedInUser = MutableLiveData<LoginUser>()
        viewModelScope.launch {
            try {
                val user = loginRepository.getLoggedInUser()
                if (user.isNotEmpty()) {
                    logedInUser.value = user.get(0)
                }

            } catch (error: Exception) {
                Log.i(TAG, "checkUserLoggedIn >>" + error.localizedMessage)
                logedInUser.value = null
            }

        }
        return logedInUser
    }

    private fun isUserNameValid(username: String): Boolean {
        return  Patterns.EMAIL_ADDRESS.matcher(username).matches()
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length >= 8
    }

}