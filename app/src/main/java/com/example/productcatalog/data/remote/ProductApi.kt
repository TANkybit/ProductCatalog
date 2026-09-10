package com.example.productcatalog.data.remote

import com.example.productcatalog.data.model.ProductResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductApi {

    @GET("products")
    suspend fun getProducts(
        @Query("limit") limit: Int,
        @Query("skip") skip: Int
    ): ProductResponse
}