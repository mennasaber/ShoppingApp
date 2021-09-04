package com.example.shoppingapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp.model.Order
import com.example.shoppingapp.repository.OrderRepository
import kotlinx.coroutines.launch

class OrderViewModel(private val orderRepository: OrderRepository) : ViewModel() {
    val response: MutableLiveData<Boolean> = MutableLiveData()
    val orderResponse: MutableLiveData<Order> = MutableLiveData()
    val ordersResponse: MutableLiveData<List<Order>> = MutableLiveData()
    fun postOrder(order: Order) {
        viewModelScope.launch {
            response.value = orderRepository.postOrder(order)
        }
    }

    fun getOrders(clientEmail: String) {
        viewModelScope.launch {
            ordersResponse.value = orderRepository.getOrders(clientEmail)
        }
    }

    fun getOrder(orderId: String) {
        viewModelScope.launch {
            orderResponse.value = orderRepository.getOrder(orderId)
        }
    }
}