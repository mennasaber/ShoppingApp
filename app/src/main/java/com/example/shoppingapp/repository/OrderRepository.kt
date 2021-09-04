package com.example.shoppingapp.repository

import com.example.shoppingapp.api.RetrofitInstance
import com.example.shoppingapp.model.Order

class OrderRepository {
    suspend fun postOrder(order: Order): Boolean {
        return RetrofitInstance.api.postOrder(order)
    }

    suspend fun getOrders(clientEmail: String): List<Order> {
        return RetrofitInstance.api.getOrders(clientEmail)
    }

    suspend fun getOrder(orderId: String): Order {
        return RetrofitInstance.api.getOrder(orderId)
    }
}