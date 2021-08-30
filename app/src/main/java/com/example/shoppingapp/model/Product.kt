package com.example.shoppingapp.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class Product(
        @SerializedName("_id")
        val id: String,
        val name:String,
        val size:String,
        val details: String,
        val price: Float,
        val category: String,
        @SerializedName("createdAt")
        val timeStamp: Date?
)