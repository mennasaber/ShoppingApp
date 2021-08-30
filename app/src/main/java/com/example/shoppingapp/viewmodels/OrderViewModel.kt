package com.example.shoppingapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp.model.Order
import com.example.shoppingapp.repository.OrderRepository
import kotlinx.coroutines.launch

class OrderViewModel(private val orderRepository: OrderRepository) : ViewModel() {
    val response: MutableLiveData<Boolean> = MutableLiveData()
    fun postOrder(order: Order) {
        viewModelScope.launch {
            response.value = orderRepository.postOrder(order)
        }
    }
}