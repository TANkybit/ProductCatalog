package com.example.productcatalog.viewmodel

import com.example.productcatalog.data.model.Product

sealed class ProductUiState {

    object Loading : ProductUiState()

    data class Success(
        val products: List<Product>
    ) : ProductUiState()

    data class Error(
        val message: String
    ) : ProductUiState()

    object Empty : ProductUiState()
}