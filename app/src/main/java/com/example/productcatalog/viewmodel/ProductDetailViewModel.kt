package com.example.productcatalog.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productcatalog.data.model.Product
import com.example.productcatalog.data.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductDetailViewModel : ViewModel() {

    private val repository = ProductRepository()

    private val _product =
        MutableStateFlow<Product?>(null)

    val product: StateFlow<Product?> =
        _product.asStateFlow()

    fun loadProduct(id: Int) {

        viewModelScope.launch {

            try {

                _product.value =
                    repository.getProduct(id)

            } catch (e: Exception) {

                e.printStackTrace()
            }
        }
    }
}