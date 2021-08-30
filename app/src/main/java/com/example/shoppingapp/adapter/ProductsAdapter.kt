package com.example.shoppingapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingapp.R
import com.example.shoppingapp.model.Product
import com.example.shoppingapp.view.ProductActivity

class ProductsAdapter(private val productsList: List<Product>) :
    RecyclerView.Adapter<ProductsAdapter.ProductViewHolder>() {
    class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var productImage: ImageView = view.findViewById(R.id.product_image)
        var productName: TextView = view.findViewById(R.id.product_name)
        var productPrice: TextView = view.findViewById(R.id.product_price)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_item, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.productPrice.text = productsList[position].price.toString()
        holder.productName.text = productsList[position].name
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, ProductActivity::class.java)
            intent.putExtra("productId", productsList[position].id)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return productsList.count()
    }
}


