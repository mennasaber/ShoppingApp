package com.example.shoppingapp.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppingapp.adapter.CartAdapter
import com.example.shoppingapp.databinding.ActivityCartBinding
import com.example.shoppingapp.model.Cart
import com.example.shoppingapp.repository.OrderRepository
import com.example.shoppingapp.util.Constants
import com.example.shoppingapp.util.OnTotalChanged
import com.example.shoppingapp.viewmodels.OrderViewModel
import com.example.shoppingapp.viewmodels.OrderViewModelFactory

class CartActivity : AppCompatActivity(), OnTotalChanged {
    lateinit var binding: ActivityCartBinding
    lateinit var orderViewModel: OrderViewModel
    lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)

        sharedPref = getSharedPreferences(Constants.USER_DATA_NAME, Context.MODE_PRIVATE)
        if (Cart.cartList.count() != 0) {
            binding.parentLayout.visibility = View.VISIBLE
            binding.subTotal.text = Cart.subTotal.toString()
            binding.totalPayment.text = (Cart.subTotal + 20).toString()
            binding.cartRecyclerView.layoutManager = LinearLayoutManager(this)
            binding.cartRecyclerView.adapter = CartAdapter()
            Cart.onTotalChanged = this
        } else binding.empty.visibility = View.VISIBLE

        setupButtonsAction()

        val orderRepository = OrderRepository()
        val orderViewModelFactory = OrderViewModelFactory(orderRepository)

        orderViewModel =
            ViewModelProvider(this, orderViewModelFactory).get(OrderViewModel::class.java)

        setContentView(binding.root)
    }

    private fun setupButtonsAction() {
        binding.back.setOnClickListener {
            finish()
        }
        binding.checkout.setOnClickListener {
            confirmOrder()
        }
    }

    private fun confirmOrder() {
        binding.progressBar.visibility = View.VISIBLE
        binding.parentLayout.visibility = View.GONE
        val order = Cart.createOrderObject(sharedPref.getString("email", "").toString())
        orderViewModel.postOrder(order)
        orderViewModel.response.observe(this, {
            if (it) {
                binding.progressBar.visibility = View.GONE
                binding.confirmedImage.visibility = View.VISIBLE
                binding.confirmedMessage.visibility = View.VISIBLE
            } else {
                binding.parentLayout.visibility = View.VISIBLE
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun assignTotalValues() {
        binding.subTotal.text = Cart.subTotal.toString()
        binding.totalPayment.text = (Cart.subTotal + 20).toString()
    }

    override fun OnSubTotalChanged() {
        if (Cart.subTotal != 0.0)
            assignTotalValues()
        else {
            binding.parentLayout.visibility = View.GONE
            binding.empty.visibility = View.VISIBLE
        }
    }
}