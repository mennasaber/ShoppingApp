package com.example.shoppingapp.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.shoppingapp.databinding.ActivitySignupBinding
import com.example.shoppingapp.model.User
import com.example.shoppingapp.repository.UserRepository
import com.example.shoppingapp.util.Constants
import com.example.shoppingapp.viewmodels.UserViewModel
import com.example.shoppingapp.viewmodels.UserViewModelFactory

class SignupActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignupBinding
    lateinit var userViewModel: UserViewModel
    lateinit var sharedPref: SharedPreferences
    lateinit var user: User
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        sharedPref = getSharedPreferences(Constants.USER_DATA_NAME, Context.MODE_PRIVATE)
        val userViewModelFactory = UserViewModelFactory(userRepository = UserRepository())
        userViewModel = ViewModelProvider(this, userViewModelFactory).get(UserViewModel::class.java)
        setupButtonsAction()
        setupObservers()
        setContentView(binding.root)
    }

    private fun setupObservers() {
        userViewModel.response.observe(this, {
            binding.progressBar.visibility = View.GONE
            if (it) {
                sharedPref.edit().putString("username", user.username).apply()
                sharedPref.edit().putString("email", user.email).apply()
                sharedPref.edit().putString("mobile_number", user.mobile_number).apply()
                goToMain()
            } else {
                Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setupButtonsAction() {
        binding.signUpButton.setOnClickListener {
            if (isValid()) {
                user = User(
                    username = binding.username.text.trim().toString(),
                    mobile_number = binding.mobileNumber.text.trim().toString(),
                    email = binding.email.text.trim().toString(),
                    password = binding.password.text.toString(),
                )
                userViewModel.postUser(user)
                binding.progressBar.visibility = View.VISIBLE
            }
        }
        binding.signIn.setOnClickListener {
            goToLogin()
        }
    }

    private fun goToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun goToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    private fun isValid(): Boolean {
        val usernameIsEmpty = binding.username.text.trim().isEmpty()
        val emailIsEmpty = binding.email.text.trim().isEmpty()
        val passwordIsEmpty = binding.password.text.trim().isEmpty()
        val mobileNumberIsEmpty = binding.mobileNumber.text.trim().isEmpty()
        if (usernameIsEmpty) {
            binding.username.error = "Invalid username"
        }
        if (mobileNumberIsEmpty) {
            binding.mobileNumber.error = "Invalid number"
        }
        if (passwordIsEmpty) {
            binding.password.error = "Invalid password"
        }
        if (emailIsEmpty) {
            binding.email.error = "Invalid email"
        }
        if (usernameIsEmpty || passwordIsEmpty || mobileNumberIsEmpty || emailIsEmpty) return false
        return true
    }
}