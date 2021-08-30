package com.example.shoppingapp.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.shoppingapp.databinding.ActivityLoginBinding
import com.example.shoppingapp.repository.UserRepository
import com.example.shoppingapp.util.Constants
import com.example.shoppingapp.viewmodels.UserViewModel
import com.example.shoppingapp.viewmodels.UserViewModelFactory

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    lateinit var userViewModel: UserViewModel
    lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        sharedPref = getSharedPreferences(Constants.USER_DATA_NAME, Context.MODE_PRIVATE)
        if (sharedPref.contains("email")) {
            goToMain()
        }
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
                userViewModel.getUser(binding.email.text.trim().toString())
            } else {
                Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show()
            }
        })
        userViewModel.userDataResponse.observe(this, {
            sharedPref.edit().putString("username", it.username).apply()
            sharedPref.edit().putString("email", it.email).apply()
            sharedPref.edit().putString("mobile_number", it.mobile_number).apply()
            goToMain()
        })
    }

    private fun setupButtonsAction() {
        binding.signInButton.setOnClickListener {
            //TODO Validate Data
            if (isValid()) {
                binding.progressBar.visibility = View.VISIBLE
                val email = binding.email.text.trim().toString()
                val password = binding.password.text.toString()
                userViewModel.validUser(email = email, password = password)
            }
        }
        binding.signUp.setOnClickListener {
            goToSignUp()
        }
    }

    private fun goToSignUp() {
        val intent = Intent(this, SignupActivity::class.java)
        startActivity(intent)
    }

    private fun goToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun isValid(): Boolean {
        val emailIsEmpty = binding.email.text.trim().isEmpty()
        val passwordIsEmpty = binding.password.text.trim().isEmpty()
        if (passwordIsEmpty) {
            binding.password.error = "Invalid password"
        }
        if (emailIsEmpty) {
            binding.email.error = "Invalid email"
        }
        if (passwordIsEmpty || emailIsEmpty) return false
        return true
    }
}