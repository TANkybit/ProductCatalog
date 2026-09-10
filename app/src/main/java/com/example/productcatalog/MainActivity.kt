package com.example.productcatalog

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.productcatalog.data.remote.RetrofitClient
import kotlinx.coroutines.launch



class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        lifecycleScope.launch {
            try {
                val response = RetrofitClient.api.getProducts(20,0)

                response.products.forEach { product ->

                    Log.d(
                        "PRODUCT_API",
                        "${product.id} - ${product.title}"
                    )
                }
            } catch (e: Exception) {

                Log.e(
                    "PRODUCT_API",
                    "Failed: ${e.message}"
                )
            }
        }




    }
}