package com.example.productcatalog.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.productcatalog.R
import com.example.productcatalog.data.model.Product
import android.widget.ImageView
import coil3.load

class ProductAdapter (private var products: List<Product>, private val onProductClick: (Product) -> Unit) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>(){
    class ProductViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val image: ImageView =
            itemView.findViewById(R.id.imgProduct)

        val title: TextView =
            itemView.findViewById(R.id.txtProductTitle)

        val price: TextView =
            itemView.findViewById(R.id.txtProductPrice)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductViewHolder {

        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_product, parent, false)

        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ProductViewHolder,
        position: Int
    ) {

        val product = products[position]

        holder.title.text = product.title
        holder.price.text = "$${product.price}"
        holder.image.load(product.thumbnail)
        holder.itemView.setOnClickListener {
            onProductClick(product)
        }
    }

    override fun getItemCount(): Int {
        return products.size
    }

    fun updateProducts(newProducts: List<Product>) {
        products = newProducts
        notifyDataSetChanged()
    }
}