package com.example.influxbeta.view.activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.influxbeta.R
import com.example.influxbeta.model.FoodData
import com.example.influxbeta.utils.Status
import com.example.influxbeta.viewmodel.MainViewModel
import com.google.gson.Gson
import org.koin.android.viewmodel.ext.android.viewModel

class Splashscreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.black, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.black));
        }

        Handler(Looper.getMainLooper()).postDelayed({

            val intent = Intent(
                applicationContext,
                MainActivity::class.java
            )
            startActivity(intent)

        }, 5000)

    }


}