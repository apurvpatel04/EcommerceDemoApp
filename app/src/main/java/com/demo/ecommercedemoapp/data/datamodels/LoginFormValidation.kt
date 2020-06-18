package com.demo.ecommercedemoapp.data.datamodels

data class LoginFormValidation(
    val usernameError: Int? = null,
    val passwordError: Int? = null,
    val isDataValid: Boolean = false
)