package com.example.productcatalog.data.repository

import com.example.productcatalog.data.model.ProductResponse
import com.example.productcatalog.data.remote.RetrofitClient

class ProductRepository {

    suspend fun getProducts(
        limit: Int,
        skip: Int
    ): ProductResponse {
        return RetrofitClient.api.getProducts(limit, skip)
    }

}