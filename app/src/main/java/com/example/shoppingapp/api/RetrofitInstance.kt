package com.example.shoppingapp.api

import com.example.shoppingapp.util.Constants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val api: ShoppingAPI by lazy {
        retrofit.create(ShoppingAPI::class.java)
    }
}