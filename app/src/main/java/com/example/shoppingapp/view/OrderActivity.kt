package com.example.shoppingapp.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppingapp.R
import com.example.shoppingapp.adapter.OrderItemAdapter
import com.example.shoppingapp.databinding.ActivityOrderBinding
import com.example.shoppingapp.model.OrderItem
import com.example.shoppingapp.model.Product
import com.example.shoppingapp.repository.MainRepository
import com.example.shoppingapp.repository.OrderRepository
import com.example.shoppingapp.viewmodels.MainViewModel
import com.example.shoppingapp.viewmodels.MainViewModelFactory
import com.example.shoppingapp.viewmodels.OrderViewModel
import com.example.shoppingapp.viewmodels.OrderViewModelFactory

class OrderActivity : AppCompatActivity() {
    lateinit var mainViewModel: MainViewModel
    lateinit var orderViewModel: OrderViewModel
    private lateinit var productsList:ArrayList<Product>
    lateinit var orderItemsList:ArrayList<OrderItem>
    lateinit var binding: ActivityOrderBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderBinding.inflate(layoutInflater)
        val orderId = intent.getStringExtra("orderId")
        productsList = arrayListOf()
        val orderViewModelFactory = OrderViewModelFactory(OrderRepository())
        orderViewModel =
            ViewModelProvider(this, orderViewModelFactory).get(OrderViewModel::class.java)
        val mainViewModelFactory = MainViewModelFactory(MainRepository())
        mainViewModel = ViewModelProvider(this, mainViewModelFactory).get(MainViewModel::class.java)
        if (orderId != null) {
            orderViewModel.getOrder(orderId)
        }
        binding.progressBar.visibility = View.VISIBLE
        setupObservers()
        setupButtonsAction()
        setContentView(binding.root)
    }
    private fun setupObservers(){
        orderViewModel.orderResponse.observe(this, {
            orderItemsList = it.orderItems as ArrayList<OrderItem>
            binding.orderDate.text = it.date.subSequence(0, 10)
            binding.orderTotal.text = it.total.toString()
            if (it.isActive) {
                binding.orderState.text = resources.getString(R.string.active)
                binding.orderState.setTextColor(
                    ContextCompat.getColor(
                        this,
                        R.color.green
                    )
                )
            } else {
                binding.orderState.text = resources.getString(R.string.delivered)
                binding.orderState.setTextColor(
                    ContextCompat.getColor(
                        this,
                        R.color.red
                    )
                )
            }
            for (item in it.orderItems){
                mainViewModel.getProductWithId(item.itemId)
            }

        })
        mainViewModel.productResponse.observe(this, {
            if (!productsList.contains(it))
                productsList.add(it)
            if (orderItemsList.count() == productsList.count()) {
                binding.progressBar.visibility = View.GONE
                val orderItemAdapter = OrderItemAdapter(productsList, orderItemsList)
                binding.itemsRecyclerView.layoutManager = LinearLayoutManager(this)
                binding.itemsRecyclerView.adapter = orderItemAdapter
            }
        })
    }
    private fun setupButtonsAction(){
        binding.back.setOnClickListener {
            finish()
        }
    }
}