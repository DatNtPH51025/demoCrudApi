package com.poly.democrudapi

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

open class RetrofitService() {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://10.24.53.243:3000/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val productService: ProductService by lazy {
        retrofit.create(ProductService::class.java)
    }
}