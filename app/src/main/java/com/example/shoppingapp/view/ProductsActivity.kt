package com.example.shoppingapp.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.shoppingapp.adapter.ProductsAdapter
import com.example.shoppingapp.databinding.ActivityProductsBinding
import com.example.shoppingapp.repository.MainRepository
import com.example.shoppingapp.viewmodels.MainViewModel
import com.example.shoppingapp.viewmodels.MainViewModelFactory

class ProductsActivity : AppCompatActivity() {
    lateinit var mainViewModel: MainViewModel
    lateinit var binding: ActivityProductsBinding
    lateinit var category: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductsBinding.inflate(layoutInflater)
        binding.progressBar.visibility = View.VISIBLE
        binding.productsRecyclerView.layoutManager = GridLayoutManager(this, 2)
        val mainRepository = MainRepository()
        val mainViewModelFactory = MainViewModelFactory(mainRepository)
        mainViewModel = ViewModelProvider(this, mainViewModelFactory).get(MainViewModel::class.java)
        category = intent.getStringExtra("category")!!
        mainViewModel.getProductsWithCategory(category)
        setupObservers()
        setupButtonsAction()
        setContentView(binding.root)
    }

    private fun setupButtonsAction() {
        binding.back.setOnClickListener {
            finish()
        }
    }

    private fun setupObservers() {
        mainViewModel.productsResponse.observe(this, {
            binding.categoryTitle.text = category
            binding.progressBar.visibility = View.GONE
            val productsAdapter = ProductsAdapter(it)
            binding.productsRecyclerView.adapter = productsAdapter
        })
    }
}