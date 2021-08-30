package com.example.shoppingapp.api

import com.example.shoppingapp.model.Order
import com.example.shoppingapp.model.Product
import com.example.shoppingapp.model.User
import com.example.shoppingapp.util.Constants
import retrofit2.http.*

interface ShoppingAPI {
    @Headers("auth:${Constants.AUTH_TOKEN}")
    @GET("/products")
    suspend fun getProducts(): List<Product>

    @Headers("auth:${Constants.AUTH_TOKEN}")
    @GET("/products/category/{category}")
    suspend fun getProductsWithCategory(@Path("category") category: String): List<Product>

    @Headers("auth:${Constants.AUTH_TOKEN}")
    @GET("/products/id/{productId}")
    suspend fun getProductWithId(@Path("productId") productId: String): Product

    @Headers("auth:${Constants.AUTH_TOKEN}")
    @POST("/orders")
    suspend fun postOrder(@Body order: Order): Boolean

    @Headers("auth:${Constants.AUTH_TOKEN}")
    @POST("/users/register")
    suspend fun postUser(@Body user: User): Boolean

    @Headers("auth:${Constants.AUTH_TOKEN}")
    @GET("/users/login/{email}/{password}")
    suspend fun getUser(@Path("email") email: String, @Path("password") password: String): Boolean

    @Headers("auth:${Constants.AUTH_TOKEN}")
    @GET("/users/{id}")
    suspend fun getUserData(@Path("id") id: String): User

}