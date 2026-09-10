package com.example.productcatalog.data.remote

import com.example.productcatalog.data.model.Product
import com.example.productcatalog.data.model.ProductResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Path

interface ProductApi {

    @GET("products")
    suspend fun getProducts(
        @Query("limit") limit: Int,
        @Query("skip") skip: Int
    ): ProductResponse

    @GET("products/{id}")
    suspend fun getProduct(
        @Path("id") id: Int
    ): Product
}