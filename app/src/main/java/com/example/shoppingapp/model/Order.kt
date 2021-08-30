package com.example.shoppingapp.model

data class Order(
    var clientEmail: String,
    val orderItems: List<OrderItem>,
    val shipping: Double,
    val total: Double
)