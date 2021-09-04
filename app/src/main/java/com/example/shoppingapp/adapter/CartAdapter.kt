package com.example.shoppingapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingapp.R
import com.example.shoppingapp.model.Cart

class CartAdapter : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
    class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val productName: TextView = view.findViewById(R.id.product_name)
        val productPrice: TextView = view.findViewById(R.id.product_price)
        val productQuantity: TextView = view.findViewById(R.id.product_quantity)
        val productSize: TextView = view.findViewById(R.id.product_size)
        val plusButton: ImageView = view.findViewById(R.id.plus_button)
        val minusButton: ImageView = view.findViewById(R.id.minus_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cart_item, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.productName.text = Cart.cartList[position].product.name
        holder.productPrice.text = Cart.cartList[position].product.price.toString()
        holder.productSize.text = Cart.cartList[position].product.size
        holder.productQuantity.text = Cart.cartList[position].quantity.toString()
        holder.minusButton.setOnClickListener {
            Cart.updateTotal(-Cart.cartList[position].product.price, 1)
            if (Cart.cartList[position].quantity == 1) Cart.cartList.remove(Cart.cartList[position])
            else if (Cart.cartList[position].quantity > 1) Cart.cartList[position].quantity -= 1
            notifyItemChanged(position)
        }
        holder.plusButton.setOnClickListener {
            Cart.cartList[position].quantity += 1
            Cart.updateTotal(Cart.cartList[position].product.price, 1)
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int {
        return Cart.cartList.count()
    }
}