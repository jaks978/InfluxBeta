package com.example.influxbeta.bottomsheet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.influxbeta.R
import com.example.influxbeta.databinding.AmoutItemBinding
import com.example.influxbeta.model.FoodCart

class FinalAmountAdapter : RecyclerView.Adapter<FinalAmountAdapter.MyViewHolder>()
{
    var foodCart:List<FoodCart> = listOf();
    lateinit var binding:AmoutItemBinding
    inner class MyViewHolder(binding:AmoutItemBinding):RecyclerView.ViewHolder(binding.root)
    {
        fun bind(item: FoodCart) {
            binding.itemName.text = item.itemaname+" ("+item.quantity+")"
            binding.itemPrice.text = (item.price*item.quantity).toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = DataBindingUtil.inflate(inflater,
            R.layout.amout_item, parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount() = foodCart.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(foodCart[position])
    }

    fun updateData(foodCart: List<FoodCart>)
    {
        this.foodCart = foodCart
    }


}
