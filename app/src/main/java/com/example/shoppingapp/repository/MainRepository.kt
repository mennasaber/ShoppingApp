package com.example.shoppingapp.repository

import com.example.shoppingapp.api.RetrofitInstance
import com.example.shoppingapp.model.Product

class MainRepository {
    suspend fun getProducts(): List<Product> {
        return RetrofitInstance.api.getProducts()
    }

    suspend fun getProductsWithCategory(category: String): List<Product> {
        return RetrofitInstance.api.getProductsWithCategory(category)
    }
    suspend fun getProductWithId(productId: String): Product {
        return RetrofitInstance.api.getProductWithId(productId)
    }
}