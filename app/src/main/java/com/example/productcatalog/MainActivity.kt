package com.example.productcatalog

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.Lifecycle
import kotlinx.coroutines.launch
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.productcatalog.ui.adapter.ProductAdapter
import com.example.productcatalog.viewmodel.ProductListViewModel
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.example.productcatalog.viewmodel.ProductUiState
import com.example.productcatalog.ui.productdetail.ProductDetailActivity
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

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

        productAdapter = ProductAdapter(emptyList()) { product ->

            val intent =
                Intent(
                    this,
                    ProductDetailActivity::class.java
                )

            intent.putExtra(
                "PRODUCT_ID",
                product.id
            )

            startActivity(intent)
        }

        recyclerProducts.layoutManager =
            LinearLayoutManager(this)

        recyclerProducts.adapter = productAdapter

        val progressBar =
            findViewById<ProgressBar>(R.id.progressBar)

        val txtEmpty =
            findViewById<TextView>(R.id.txtEmpty)

        val errorLayout =
            findViewById<LinearLayout>(R.id.errorLayout)

        val txtError =
            findViewById<TextView>(R.id.txtError)

        val btnRetry =
            findViewById<Button>(R.id.btnRetry)

        val edtSearch =
            findViewById<EditText>(R.id.edtSearch)

        val swipeRefresh =
            findViewById<SwipeRefreshLayout>(R.id.swipeRefresh)

        lifecycleScope.launch {

            repeatOnLifecycle(Lifecycle.State.STARTED) {

                viewModel.uiState.collect { state ->

                    if (state !is ProductUiState.Loading) {
                        swipeRefresh.isRefreshing = false
                    }

                    recyclerProducts.visibility = View.GONE
                    progressBar.visibility = View.GONE
                    txtEmpty.visibility = View.GONE
                    errorLayout.visibility = View.GONE

                    when (state) {

                        is ProductUiState.Loading -> {
                            progressBar.visibility = View.VISIBLE
                        }

                        is ProductUiState.Success -> {
                            recyclerProducts.visibility = View.VISIBLE
                            productAdapter.updateProducts(state.products)


                        }

                        is ProductUiState.Empty -> {
                            txtEmpty.visibility = View.VISIBLE
                        }

                        is ProductUiState.Error -> {
                            errorLayout.visibility = View.VISIBLE
                            txtError.text = state.message
                        }
                    }
                }
            }
        }

        viewModel.loadProducts()

        btnRetry.setOnClickListener {
            viewModel.loadProducts()
        }

        recyclerProducts.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {

                override fun onScrolled(
                    recyclerView: RecyclerView,
                    dx: Int,
                    dy: Int
                ) {

                    super.onScrolled(recyclerView, dx, dy)

                    val layoutManager =
                        recyclerView.layoutManager
                                as LinearLayoutManager

                    val visibleItemCount =
                        layoutManager.childCount

                    val totalItemCount =
                        layoutManager.itemCount

                    val firstVisibleItemPosition =
                        layoutManager.findFirstVisibleItemPosition()

                    if (
                        visibleItemCount +
                        firstVisibleItemPosition
                        >= totalItemCount - 3
                    ) {

                        viewModel.loadNextPage()
                    }
                }
            }
        )

        edtSearch.addTextChangedListener(
            object : TextWatcher {

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                    viewModel.searchProducts(
                        s.toString()
                    )
                }

                override fun afterTextChanged(
                    s: Editable?
                ) {
                }
            }
        )

        swipeRefresh.setOnRefreshListener {

            if (edtSearch.text.isNotEmpty()) {
                edtSearch.text.clear()
            } else {
                viewModel.loadProducts()
            }
        }
    }

}