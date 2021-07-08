package com.example.influxbeta

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.influxbeta.bottomsheet.BottomSheetCart
import com.example.influxbeta.databinding.ActivityMainBinding
import com.example.influxbeta.fragments.BasicFragment
import com.example.influxbeta.model.FoodCart
import com.example.influxbeta.model.FoodData
import com.example.influxbeta.utils.Util
import com.google.gson.Gson
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class MainActivity : AppCompatActivity()
{
    lateinit var binding:ActivityMainBinding
    companion object
    {
        lateinit var currencyType:String
        var foodcartData = HashMap<Int,FoodCart>()
    }
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.black, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.black));
        }
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        binding.tabLayout!!.setupWithViewPager(binding.pager)
        binding.bottomLayout.setOnClickListener {
            BottomSheetCart().apply {
                show(supportFragmentManager, BottomSheetCart.TAG)
            }
        }

        val foodDataJson = intent.getStringExtra("foodData");
        val gson = Gson()
        val foodData = gson.fromJson(foodDataJson,FoodData::class.java)
        if(foodData!=null)
        {
            setupViewPager(foodData)
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

    internal inner class ViewPagerAdapter(manager: FragmentManager) : FragmentStatePagerAdapter(manager) {
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
        val datastr = currencyType+" "+amount
        binding.priceTv.text = datastr
    }

}

