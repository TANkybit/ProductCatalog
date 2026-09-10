package com.example.productcatalog.ui.productdetail

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.productcatalog.R
import com.example.productcatalog.viewmodel.ProductDetailViewModel
import androidx.activity.viewModels
import coil3.load
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle

class ProductDetailActivity : AppCompatActivity() {
    private val viewModel: ProductDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_product_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val imgDetail =
            findViewById<ImageView>(R.id.imgDetail)

        val txtTitle =
            findViewById<TextView>(R.id.txtDetailTitle)

        val txtPrice =
            findViewById<TextView>(R.id.txtDetailPrice)

        val txtRating =
            findViewById<TextView>(R.id.txtDetailRating)

        val txtDescription =
            findViewById<TextView>(R.id.txtDetailDescription)

        val productId =
            intent.getIntExtra("PRODUCT_ID", -1)

        lifecycleScope.launch {

            repeatOnLifecycle(Lifecycle.State.STARTED) {

                viewModel.product.collect { product ->

                    if (product != null) {

                        txtTitle.text = product.title
                        txtPrice.text = "$${product.price}"
                        txtRating.text =
                            "Rating: ${product.rating}"

                        txtDescription.text =
                            product.description

                        imgDetail.load(
                            product.images.firstOrNull()
                                ?: product.thumbnail
                        )
                    }
                }
            }
        }
        if (productId != -1) {
            viewModel.loadProduct(productId)
        }
    }
}