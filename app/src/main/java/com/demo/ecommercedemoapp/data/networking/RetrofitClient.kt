package com.demo.ecommercedemoapp.data.networking



import com.demo.ecommercedemoapp.BuildConfig
import com.demo.ecommercedemoapp.data.datamodels.FilterData
import com.demo.ecommercedemoapp.data.datamodels.LoginUser
import com.demo.ecommercedemoapp.data.datamodels.ProductApiRes
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query


private val apiService :ApiInterface by lazy {





    //auth token not required
    /*
    val okHttpClientBuilder
    if (authToken.isNotEmpty()){
        val interceptor = AuthenticationInterceptor(authToken)
        okHttpClientBuilder.addInterceptor(interceptor)
    }*/

    val okHttpClient =  OkHttpClient.Builder().build()


    val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

     retrofit.create(ApiInterface::class.java)
}

fun getNetworkService() = apiService

/*class AuthenticationInterceptor(val authToken: String) : Interceptor {


    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        requestBuilder.addHeader("Content-Type", "application/json");
        return chain.proceed(requestBuilder.build())
    }
}*/

interface ApiInterface {

    @POST("customer/customerlogin")
    suspend fun loginApi(
        @Query("email") email: String,
        @Query("password") password: String
    ): LoginUser

    @POST("category/products")
    suspend fun getAllProducts(@Body filterData: FilterData): ProductApiRes

}