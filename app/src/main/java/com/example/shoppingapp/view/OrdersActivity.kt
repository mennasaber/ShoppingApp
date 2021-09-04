package com.example.shoppingapp.view

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppingapp.adapter.OrdersAdapter
import com.example.shoppingapp.databinding.ActivityOrdersBinding
import com.example.shoppingapp.repository.OrderRepository
import com.example.shoppingapp.util.Constants
import com.example.shoppingapp.viewmodels.OrderViewModel
import com.example.shoppingapp.viewmodels.OrderViewModelFactory

class OrdersActivity : AppCompatActivity() {
    lateinit var orderViewModel: OrderViewModel
    lateinit var binding: ActivityOrdersBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrdersBinding.inflate(layoutInflater)
        val clientEmail =
            getSharedPreferences(Constants.USER_DATA_NAME, Context.MODE_PRIVATE).getString(
                "email",
                "null"
            )
        val orderViewModelFactory = OrderViewModelFactory(OrderRepository())
        orderViewModel =
            ViewModelProvider(this, orderViewModelFactory).get(OrderViewModel::class.java)
        binding.progressBar.visibility = View.VISIBLE
        orderViewModel.getOrders(clientEmail!!)
        orderViewModel.ordersResponse.observe(this, {
            binding.progressBar.visibility = View.GONE
            val ordersAdapter = OrdersAdapter(it)
            binding.ordersRecyclerView.layoutManager = LinearLayoutManager(this)
            binding.ordersRecyclerView.adapter = ordersAdapter
        })
        binding.back.setOnClickListener {
            finish()
        }
        setContentView(binding.root)
    }
}