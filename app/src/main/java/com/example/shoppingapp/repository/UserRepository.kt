package com.example.shoppingapp.repository

import com.example.shoppingapp.api.RetrofitInstance
import com.example.shoppingapp.model.User

class UserRepository {
    suspend fun postUser(user: User):Boolean{
       return RetrofitInstance.api.postUser(user)
    }
    suspend fun getUser(email:String,password:String):Boolean{
        return RetrofitInstance.api.getUser(email,password)
    }

    suspend fun getUserData(email: String):User{
        return RetrofitInstance.api.getUserData(email)
    }
}