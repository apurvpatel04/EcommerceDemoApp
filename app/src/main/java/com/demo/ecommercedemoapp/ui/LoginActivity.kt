package com.demo.ecommercedemoapp.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.demo.ecommercedemoapp.R
import com.demo.ecommercedemoapp.data.datamodels.Status
import com.demo.ecommercedemoapp.utils.hideProgressBar
import com.demo.ecommercedemoapp.utils.loadProgressBar
import com.demo.ecommercedemoapp.viewmodels.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)


        //checks whether user is already logedin or not
        // if user is logged in use db to get user data and launch main product list
        loginViewModel.checkUserLoggedIn().observe(this, Observer { user ->
            if (user != null) {
                val intent = Intent(this, ProductListActivity::class.java)
                intent.putExtra("authToken", user.customer_token)
                startActivity(intent)
                finish()
            }

        })

        // email and password validation method setup
        setupFormValidation()

        // login user livedata observable code on click of login button
        loginUserSetup()


    }

    fun loginUserSetup() {
        loginViewModel.loginUser.observe(this, Observer { serverRes ->
            when (serverRes.status) {
                Status.SUCCESS -> {
                    loginProgressBar.hideProgressBar()
                    Toast.makeText(this, serverRes.data?.message, Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, ProductListActivity::class.java)
                    intent.putExtra("authToken", serverRes.data?.customer_token)
                    startActivity(intent)
                    finish()
                }
                Status.ERROR -> {
                    loginProgressBar.hideProgressBar()
                    Toast.makeText(this, serverRes.message, Toast.LENGTH_SHORT).show()
                }
                Status.LOADING -> {
                    loginProgressBar.loadProgressBar()
                    Toast.makeText(this, serverRes.message, Toast.LENGTH_SHORT).show()
                }
            }

        })

        loginButton.setOnClickListener {
            val email: String = userNameET.text.toString()
            val password: String = passwordET.text.toString()
            loginViewModel.loginWithCredentials(email, password)
        }
    }

    fun setupFormValidation() {
        loginViewModel.loginFormValidation.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            loginButton.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                userNameET.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                passwordET.error = getString(loginState.passwordError)
            }
        })


        userNameET.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                loginViewModel.validateData(
                    userNameET.text.toString(),
                    passwordET.text.toString()
                )
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })

        passwordET.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                loginViewModel.validateData(
                    userNameET.text.toString(),
                    passwordET.text.toString()
                )
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })
    }
}