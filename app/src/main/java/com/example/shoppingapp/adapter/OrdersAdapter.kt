package com.example.shoppingapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingapp.R
import com.example.shoppingapp.model.Order
import com.example.shoppingapp.view.OrderActivity

class OrdersAdapter(val ordersList: List<Order>) :
    RecyclerView.Adapter<OrdersAdapter.OrderViewHolder>() {
    class OrderViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val orderDate: TextView = view.findViewById(R.id.order_date)
        val orderState: TextView = view.findViewById(R.id.order_state)
        val detailsButton: Button = view.findViewById(R.id.order_details_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.order_item, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.orderDate.text = ordersList[position].date.subSequence(0, 10)
        if (ordersList[position].isActive) {
            holder.orderState.text = holder.itemView.context.resources.getString(R.string.active)
            holder.orderState.setTextColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.green
                )
            )
        } else {
            holder.orderState.text = holder.itemView.context.resources.getString(R.string.delivered)
            holder.orderState.setTextColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.red
                )
            )
        }
        holder.detailsButton.setOnClickListener {
            goToDetails(holder.itemView.context,ordersList[position])
        }
    }

    private fun goToDetails(context: Context, order: Order) {
        val intent = Intent(context,OrderActivity::class.java)
        intent.putExtra("orderId",order.id)
        context.startActivity(intent)
    }

    override fun getItemCount(): Int {
        return ordersList.count()
    }
}