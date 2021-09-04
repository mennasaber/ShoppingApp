package com.example.shoppingapp.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Order(
    @SerializedName("_id")
    val id: String,
    var clientEmail: String,
    val orderItems: List<OrderItem>,
    val shipping: Double,
    val total: Double,
    @SerializedName("createdAt")
    val date: String,
    val isActive: Boolean
)