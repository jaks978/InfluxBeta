package com.example.influxbeta.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.influxbeta.MainActivity
import com.example.influxbeta.R
import com.example.influxbeta.databinding.LayoutFinalAmountBinding
import com.example.influxbeta.model.FoodCart
import com.example.influxbeta.utils.Util
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetCart : BottomSheetDialogFragment()
{
    companion object {

        const val TAG = "CustomBottomSheetDialogFragment"
       lateinit var foodCartlist:List<FoodCart>

    }

    lateinit var binding: LayoutFinalAmountBinding
    lateinit var adapter:FinalAmountAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.layout_final_amount,container,false)
        binding.recyclerView.layoutManager = LinearLayoutManager(activity,
            RecyclerView.VERTICAL,false)
        adapter = FinalAmountAdapter()
        binding.recyclerView.adapter = adapter
        adapter.updateData(foodCartlist)
        var amount =0.0
        foodCartlist.forEach {
            val quanti = it.quantity
            val price = it.price
            if(quanti>=1)
            {
                amount += Util.amount_out_quantityNprice_In(quanti, price)
            }

        }
        val datastr = MainActivity.currencyType +" "+amount
        binding.priceTv.text = datastr
        return binding.root
    }

}