package com.example.influxbeta.view.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.Observer
import com.example.influxbeta.R
import com.example.influxbeta.view.bottomsheet.BottomSheetCart
import com.example.influxbeta.databinding.ActivityMainBinding
import com.example.influxbeta.view.fragments.BasicFragment
import com.example.influxbeta.model.FoodCart
import com.example.influxbeta.model.FoodData
import com.example.influxbeta.utils.Status
import com.example.influxbeta.utils.Util
import com.example.influxbeta.viewmodel.MainViewModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.koin.android.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity()
{
    lateinit var binding:ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModel()
    companion object
    {
        lateinit var currencyType:String
        var foodcartData = HashMap<Int,FoodCart>()
    }
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupObserver()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.black, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.black));
        }
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_main
        )

        binding.tabLayout!!.setupWithViewPager(binding.pager)
        binding.bottomLayout.setOnClickListener {
            BottomSheetCart().apply {
                show(supportFragmentManager, BottomSheetCart.TAG)
            }
        }
    }

    private fun setupViewPager(foodData: FoodData?)
    {
        currencyType = foodData?.currency.toString()
        val adapter = ViewPagerAdapter(supportFragmentManager)
        foodData?.foodList?.forEach {
                food-> adapter.addFragment(BasicFragment(food.fnblist),food.tabName)
        }
        binding.pager.adapter = adapter
    }

    //1 add behaviour
    //BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,
    internal inner class ViewPagerAdapter(manager: FragmentManager) : FragmentStatePagerAdapter(manager,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        private val mFragmentList = ArrayList<Fragment>()
        private val mFragmentTitleList = ArrayList<String>()

        override fun getItem(position: Int): Fragment {
            return mFragmentList[position]
        }

        override fun getCount(): Int {
            return mFragmentList.size
        }

        fun addFragment(fragment: Fragment, title: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence {
            return mFragmentTitleList[position]
        }
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: HashMap<Int,FoodCart>) { /* Do something */
        val foodCartData = mutableListOf<FoodCart>()
        for ((key, value) in event.entries) {
            println(key.toString() + " " + value)
            foodCartData.add(value)
        }

        updateSheet(foodCartData)
        updateBottomTab(foodCartData)
    }

    fun updateSheet(event: List<FoodCart>)
    {
        binding.bottomLayout.visibility = View.VISIBLE
        BottomSheetCart.foodCartlist = event
    }

    fun updateBottomTab(event: List<FoodCart>)
    {
        var amount =0.0
        event.forEach {
          val quanti = it.quantity
            val price = it.price
            if(quanti>=1)
            {
                amount += Util.amount_out_quantityNprice_In(quanti, price)
            }

        }
        val datastr = currencyType +" "+amount
        binding.priceTv.text = datastr
    }

    private fun setupObserver() {
        mainViewModel.foodData.observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    Handler(Looper.getMainLooper()).postDelayed({
                        progress_layout.visibility = View.GONE

                    }, 1500)
                    it.data?.let { users -> renderList(users) }
                }
                Status.LOADING -> {
                    progress_layout.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    progress_layout.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun renderList(foodData:  FoodData) {
        setupViewPager(foodData)
    }

}

