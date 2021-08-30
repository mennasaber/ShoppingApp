package com.example.shoppingapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp.model.User
import com.example.shoppingapp.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(val userRepository: UserRepository):ViewModel() {
    val response :MutableLiveData<Boolean> = MutableLiveData()
    val userDataResponse :MutableLiveData<User> = MutableLiveData()
    fun postUser(user: User){
        viewModelScope.launch {
            response.value = userRepository.postUser(user)
        }
    }
    fun validUser(email:String,password:String){
        viewModelScope.launch {
            response.value = userRepository.getUser(email,password)
        }
    }
    fun getUser(email:String){
        viewModelScope.launch {
            userDataResponse.value = userRepository.getUserData(email)
        }
    }
}