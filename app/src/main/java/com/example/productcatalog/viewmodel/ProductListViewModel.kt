package com.example.productcatalog.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productcatalog.data.model.Product
import com.example.productcatalog.data.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductListViewModel : ViewModel() {
    private val repository = ProductRepository()

    private val _products = MutableStateFlow<List<Product>>(emptyList())

    val products: StateFlow<List<Product>> =
        _products.asStateFlow()

    fun loadProducts() {

        viewModelScope.launch {

            try {

                val response =
                    repository.getProducts(20, 0)

                _products.value = response.products

            } catch (e: Exception) {

                e.printStackTrace()
            }
        }
    }
}