package com.example.influxbeta.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.influxbeta.adapter.FoodDataAdapter
import com.example.influxbeta.R
import com.example.influxbeta.databinding.FragmentLayoutBinding
import com.example.influxbeta.model.Fnblist

class BasicFragment(val foodData: List<Fnblist>) : Fragment()
{

    lateinit   var binding: FragmentLayoutBinding
    lateinit var adapter: FoodDataAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_layout,container,false)
        binding.recyclerview.layoutManager = LinearLayoutManager(activity,
            RecyclerView.VERTICAL,false)
        adapter = FoodDataAdapter(foodData)
        binding.recyclerview.adapter = adapter
        return binding.root
    }
}
