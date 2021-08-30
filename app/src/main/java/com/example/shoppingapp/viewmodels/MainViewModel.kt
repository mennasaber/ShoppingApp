package com.example.shoppingapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp.model.Product
import com.example.shoppingapp.repository.MainRepository
import kotlinx.coroutines.launch

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {
    val response: MutableLiveData<List<Product>> = MutableLiveData()
    val productsResponse: MutableLiveData<List<Product>> = MutableLiveData()
    val productResponse: MutableLiveData<Product> = MutableLiveData()
    fun getProducts() {
        viewModelScope.launch {
            response.value = mainRepository.getProducts()
        }
    }

    fun getProductsWithCategory(category: String) {
        viewModelScope.launch {
            productsResponse.value = mainRepository.getProductsWithCategory(category)
        }
    }

    fun getProductWithId(productId: String) {
        viewModelScope.launch {
            productResponse.value = mainRepository.getProductWithId(productId)
        }
    }
}