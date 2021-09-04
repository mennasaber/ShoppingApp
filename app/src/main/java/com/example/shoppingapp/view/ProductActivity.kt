package com.example.shoppingapp.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.shoppingapp.databinding.ActivityProductBinding
import com.example.shoppingapp.model.Cart
import com.example.shoppingapp.model.CartItem
import com.example.shoppingapp.model.Product
import com.example.shoppingapp.repository.MainRepository
import com.example.shoppingapp.viewmodels.MainViewModel
import com.example.shoppingapp.viewmodels.MainViewModelFactory

class ProductActivity : AppCompatActivity() {
    lateinit var mainViewModel: MainViewModel
    lateinit var product: Product
    lateinit var binding: ActivityProductBinding
    lateinit var productId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductBinding.inflate(layoutInflater)
        productId = intent.getStringExtra("productId")!!
        changeWidgetsVisibility(View.GONE)
        binding.progressBar.visibility = View.VISIBLE
        val mainRepository = MainRepository()
        val mainViewModelFactory = MainViewModelFactory(mainRepository)
        mainViewModel = ViewModelProvider(this, mainViewModelFactory).get(MainViewModel::class.java)
        mainViewModel.getProductWithId(productId)
        setupObservers()
        setContentView(binding.root)
        setupButtonsAction()
    }

    private fun setupObservers() {
        mainViewModel.productResponse.observe(this, {
            product = it
            binding.progressBar.visibility = View.GONE
            changeWidgetsVisibility(View.VISIBLE)
            binding.productName.text = it.name
            binding.productDetails.text = it.details
            binding.productPrice.text = it.price.toString()
            binding.productSize.text = it.size
        })
    }

    private fun setupButtonsAction() {
        binding.back.setOnClickListener {
            finish()
        }
        binding.addToCart.setOnClickListener {
            if (this::product.isInitialized) {
                val itemIndex = Cart.cartList.indexOfFirst { it.product.id == productId }
                if (itemIndex == -1) Cart.cartList.add(CartItem(product, 1))
                else Cart.cartList[itemIndex].quantity += 1
                Cart.updateTotal(product.price, 1)
            }
        }
    }

    private fun changeWidgetsVisibility(state: Int) {
        binding.productImage.visibility = state
        binding.dolarSign.visibility = state
    }
}