package com.poly.democrudapi

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductService {
    // day la anitation cua retrofit, dung 1 http get
    @GET("sanPham")
    suspend fun getLit(): Response<List<ProductResponse>>

    @GET("sanPham/{id}")
    suspend fun getDetal(@Path("id") id:String): Response<ProductResponse>
}