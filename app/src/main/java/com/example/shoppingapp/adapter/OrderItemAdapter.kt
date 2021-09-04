package com.example.shoppingapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingapp.R
import com.example.shoppingapp.model.OrderItem
import com.example.shoppingapp.model.Product

class OrderItemAdapter(private val orderProducts:List<Product>, private val orderItems:List<OrderItem>):RecyclerView.Adapter<OrderItemAdapter.OrderItemViewHolder>()
{
    class OrderItemViewHolder(view:View) :RecyclerView.ViewHolder(view){
        val productName: TextView = view.findViewById(R.id.product_name)
        val productPrice: TextView = view.findViewById(R.id.product_price)
        val productQuantity: TextView = view.findViewById(R.id.product_quantity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_item_2,parent,false)
        return OrderItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderItemViewHolder, position: Int) {
        holder.productName.text = orderProducts[position].name
        holder.productPrice.text = orderProducts[position].price.toString()
        holder.productQuantity.text = orderItems[position].quantity.toString()
    }

    override fun getItemCount(): Int { return orderItems.count()
    }
}