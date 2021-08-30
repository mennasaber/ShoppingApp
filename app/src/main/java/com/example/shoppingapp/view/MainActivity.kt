package com.example.shoppingapp.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppingapp.adapter.CategoriesAdapter
import com.example.shoppingapp.databinding.ActivityMainBinding
import com.example.shoppingapp.model.Category
import com.example.shoppingapp.repository.MainRepository
import com.example.shoppingapp.viewmodels.MainViewModel
import com.example.shoppingapp.viewmodels.MainViewModelFactory


class MainActivity : AppCompatActivity() {
    lateinit var mainViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        binding.progressBar.visibility = View.VISIBLE
        setContentView(binding.root)
        binding.categoriesRecyclerView.layoutManager =
            LinearLayoutManager(this)
        val categories = arrayListOf<Category>()
        val mainRepository = MainRepository()
        val mainViewModelFactory = MainViewModelFactory(mainRepository)

        mainViewModel = ViewModelProvider(this, mainViewModelFactory).get(MainViewModel::class.java)
        mainViewModel.getProducts()
        mainViewModel.response.observe(this, { it ->
            binding.progressBar.visibility = View.GONE
            val categorization = it.groupBy { it.category }
            for ((key, value) in categorization) {
                categories.add(Category("0", key, value))
            }
            val categoriesAdapter = CategoriesAdapter(categories)
            binding.categoriesRecyclerView.adapter = categoriesAdapter
        })
        binding.cartIcon.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }
    }
}