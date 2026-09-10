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

    private val _uiState =
        MutableStateFlow<ProductUiState>(ProductUiState.Loading)

    val uiState: StateFlow<ProductUiState> =
        _uiState.asStateFlow()

    fun loadProducts() {

        viewModelScope.launch {

            _uiState.value = ProductUiState.Loading

            try {

                val response =
                    repository.getProducts(20, 0)

                if (response.products.isEmpty()) {
                    _uiState.value = ProductUiState.Empty
                } else {
                    _uiState.value =
                        ProductUiState.Success(response.products)
                }

            } catch (e: Exception) {

                _uiState.value =
                    ProductUiState.Error(
                        e.message ?: "Something went wrong"
                    )
            }
        }
    }
}