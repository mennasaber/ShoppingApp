package com.example.shoppingapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shoppingapp.repository.OrderRepository

class OrderViewModelFactory(private val orderRepository: OrderRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return OrderViewModel(orderRepository) as T
    }
}