package com.example.shoppingapp.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppingapp.R
import com.example.shoppingapp.adapter.CategoriesAdapter
import com.example.shoppingapp.databinding.ActivityMainBinding
import com.example.shoppingapp.model.Category
import com.example.shoppingapp.repository.MainRepository
import com.example.shoppingapp.util.Constants
import com.example.shoppingapp.viewmodels.MainViewModel
import com.example.shoppingapp.viewmodels.MainViewModelFactory


class MainActivity : AppCompatActivity() {
    lateinit var mainViewModel: MainViewModel
    lateinit var binding: ActivityMainBinding
    lateinit var categories: ArrayList<Category>
    lateinit var sharedPref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.navView.itemIconTintList=null
        binding.progressBar.visibility = View.VISIBLE
        setContentView(binding.root)
        binding.categoriesRecyclerView.layoutManager =
            LinearLayoutManager(this)
        categories = arrayListOf()
        val mainViewModelFactory = MainViewModelFactory(MainRepository())
        mainViewModel = ViewModelProvider(this, mainViewModelFactory).get(MainViewModel::class.java)
        mainViewModel.getProducts()
        setupObservers()
        setupButtonsAction()
        setupUserData()
    }

    private fun setupUserData() {
        sharedPref = getSharedPreferences(Constants.USER_DATA_NAME, Context.MODE_PRIVATE)
        binding.navView.getHeaderView(0).findViewById<TextView>(R.id.username).text =
            sharedPref.getString("username", "null")
        binding.navView.getHeaderView(0).findViewById<TextView>(R.id.email).text =
            sharedPref.getString("email", "null")
    }

    private fun goToLogin() {
        sharedPref.edit().clear().apply()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun goToCart() {
        val intent = Intent(this, CartActivity::class.java)
        startActivity(intent)
    }

    private fun setupButtonsAction() {
        binding.cartIcon.setOnClickListener {
            goToCart()
        }
        binding.circleImageView.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.orders_history -> goToOrders()
                R.id.log_out -> goToLogin()
            }
            true
        }
    }

    private fun goToOrders() {
        val intent = Intent(this, OrdersActivity::class.java)
        startActivity(intent)
    }

    private fun setupObservers() {
        mainViewModel.response.observe(this, { it ->
            binding.progressBar.visibility = View.GONE
            val categorization = it.groupBy { it.category }
            for ((key, value) in categorization) {
                categories.add(Category("0", key, value))
            }
            val categoriesAdapter = CategoriesAdapter(categories)
            binding.categoriesRecyclerView.adapter = categoriesAdapter
        })
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isOpen) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            return
        }
    }
}