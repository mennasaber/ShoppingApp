package com.example.shoppingapp.model

data class Category(
    val id:String,
    val name :String,
    val products:List<Product>
)