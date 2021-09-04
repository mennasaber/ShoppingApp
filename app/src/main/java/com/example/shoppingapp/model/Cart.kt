package com.example.shoppingapp.model

import com.example.shoppingapp.util.OnTotalChanged

object Cart {
    var subTotal: Double = 0.0
    lateinit var onTotalChanged: OnTotalChanged
    val cartList: ArrayList<CartItem> by lazy {
        arrayListOf()
    }

    fun updateTotal(price: Float, quantity: Int) {
        subTotal += price * quantity
        if (this::onTotalChanged.isInitialized)
            onTotalChanged.OnSubTotalChanged()
    }

    fun createOrderObject(clientEmail: String): Order {
        val order = Order(
            "",
            clientEmail, cartList.map { OrderItem(it.product.id, it.quantity) }, 20.0,
            subTotal + 20, "", true
        )
        cartList.clear()
        subTotal = 0.0
        return order
    }
}