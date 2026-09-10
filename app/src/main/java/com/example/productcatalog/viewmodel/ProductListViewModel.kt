package com.example.productcatalog.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productcatalog.data.model.Product
import com.example.productcatalog.data.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.CancellationException

class ProductListViewModel : ViewModel() {
    private val repository = ProductRepository()

    private val _uiState =
        MutableStateFlow<ProductUiState>(ProductUiState.Loading)

    val uiState: StateFlow<ProductUiState> =
        _uiState.asStateFlow()

    private val allProducts = mutableListOf<Product>()

    private var totalProducts = Int.MAX_VALUE

    private var isLoading = false

    private var searchJob: Job? = null

    private var isSearching = false

    fun loadProducts() {

        isSearching = false

        if (isLoading) return

        isLoading = true

        viewModelScope.launch {

            _uiState.value = ProductUiState.Loading

            try {

                val response = repository.getProducts(20, 0)

                allProducts.clear()
                allProducts.addAll(response.products)

                totalProducts = response.total

                if (allProducts.isEmpty()) {

                    _uiState.value = ProductUiState.Empty

                } else {

                    _uiState.value =
                        ProductUiState.Success(allProducts.toList())
                }

            } catch (e: Exception) {

                _uiState.value =
                    ProductUiState.Error(
                        e.message ?: "Something went wrong"
                    )

            } finally {

                isLoading = false
            }
        }
    }

    fun loadNextPage() {

        if (isLoading) return

        if (isSearching) return

        if (allProducts.size >= totalProducts) return

        isLoading = true

        viewModelScope.launch {

            try {

                val response =
                    repository.getProducts(
                        20,
                        allProducts.size
                    )

                allProducts.addAll(response.products)

                totalProducts = response.total

                _uiState.value =
                    ProductUiState.Success(
                        allProducts.toList()
                    )

            } catch (e: Exception) {

                e.printStackTrace()

            } finally {

                isLoading = false
            }
        }
    }

    fun searchProducts(query: String) {
        isSearching = query.isNotBlank()

        searchJob?.cancel()

        searchJob = viewModelScope.launch {

            delay(200)

            if (query.isBlank()) {
                loadProducts()
                return@launch
            }

            _uiState.value = ProductUiState.Loading

            try {

                val response =
                    repository.searchProducts(query)

                val sortedProducts =
                    response.products.sortedByDescending { product ->
                        product.title.contains(
                            query,
                            ignoreCase = true
                        )
                    }

                if (response.products.isEmpty()) {

                    _uiState.value =
                        ProductUiState.Empty

                } else {

                    _uiState.value =
                        ProductUiState.Success(
                            sortedProducts
                        )
                }

            } catch (e: CancellationException) {

                throw e

            } catch (e: Exception) {

                _uiState.value =
                    ProductUiState.Error(
                        e.message ?: "Something went wrong"
                    )
            }
        }
    }
}