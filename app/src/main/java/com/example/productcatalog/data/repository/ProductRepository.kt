package com.example.productcatalog.data.repository

import com.example.productcatalog.data.model.ProductResponse
import com.example.productcatalog.data.remote.RetrofitClient
import com.example.productcatalog.data.model.Product
class ProductRepository {

    suspend fun getProducts(
        limit: Int,
        skip: Int
    ): ProductResponse {
        return RetrofitClient.api.getProducts(limit, skip)
    }

    suspend fun getProduct(id: Int): Product {
        return RetrofitClient.api.getProduct(id)
    }
}