package com.example.influxbeta

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.influxbeta.model.FoodData
import com.example.influxbeta.utils.Status
import com.example.influxbeta.viewmodel.MainViewModel
import com.google.gson.Gson
import org.koin.android.viewmodel.ext.android.viewModel

class Splashscreen : AppCompatActivity()
{
    private val mainViewModel: MainViewModel by viewModel()
    lateinit var foodData: FoodData
    var networkcall = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.black, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.black));
        }
        Handler(Looper.getMainLooper()).postDelayed({
            if(networkcall)
            {
                val intent = Intent(applicationContext,MainActivity::class.java)
                intent.putExtra("foodData", Gson().toJson(foodData))
                startActivity(intent)
            }
            else
            {
                Toast.makeText(applicationContext,"Received 0 times to show",Toast.LENGTH_LONG).show()
            }

        }, 5000)

        setupObserver()
    }

    private fun setupObserver() {
        mainViewModel.foodData.observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    networkcall = true
                    it.data?.let { users -> renderList(users) }
                }
                Status.LOADING -> {

                }
                Status.ERROR -> {
                    networkcall = false
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun renderList(foodData:  FoodData) {
        this.foodData = foodData
    }
}