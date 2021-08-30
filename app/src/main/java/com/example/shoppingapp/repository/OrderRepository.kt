package com.example.shoppingapp.repository

import com.example.shoppingapp.api.RetrofitInstance
import com.example.shoppingapp.model.Order

class OrderRepository {
    suspend fun postOrder(order: Order) : Boolean {
        return RetrofitInstance.api.postOrder(order)
    }
}