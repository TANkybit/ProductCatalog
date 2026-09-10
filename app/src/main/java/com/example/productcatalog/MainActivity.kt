package com.example.productcatalog

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.Lifecycle
import com.example.productcatalog.viewmodel.ProductListViewModel
import kotlinx.coroutines.launch
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.productcatalog.ui.adapter.ProductAdapter

class MainActivity : AppCompatActivity() {
    private val viewModel: ProductListViewModel by viewModels()

    private lateinit var productAdapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val recyclerProducts =
            findViewById<RecyclerView>(R.id.recyclerProducts)

        productAdapter = ProductAdapter(emptyList())

        recyclerProducts.layoutManager =
            LinearLayoutManager(this)

        recyclerProducts.adapter = productAdapter

        lifecycleScope.launch {

            repeatOnLifecycle(Lifecycle.State.STARTED) {

                viewModel.products.collect { products ->

                    productAdapter.updateProducts(products)
                }
            }
        }

        viewModel.loadProducts()
    }
}