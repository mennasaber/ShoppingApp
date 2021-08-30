package com.example.shoppingapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingapp.R
import com.example.shoppingapp.model.Category
import com.example.shoppingapp.view.ProductsActivity

class CategoriesAdapter(val categoriesList: List<Category>) :
    RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {
    class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val categoryTitle: TextView = view.findViewById(R.id.category_title)
        val productsRecyclerView: RecyclerView = view.findViewById(R.id.products_recyclerView)
        val viewAll: TextView = view.findViewById(R.id.view_all)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.categoryTitle.text = categoriesList[position].name
        holder.productsRecyclerView.layoutManager =
            LinearLayoutManager(holder.itemView.context, LinearLayoutManager.HORIZONTAL, false)
        val productsAdapter = ProductsAdapter(categoriesList[position].products)
        holder.productsRecyclerView.adapter = productsAdapter
        holder.viewAll.setOnClickListener {
            val intent = Intent(holder.itemView.context, ProductsActivity::class.java)
            intent.putExtra("category", categoriesList[position].name)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return categoriesList.count()
    }
}