package com.example.influxbeta.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.influxbeta.adapter.FoodDataAdapter.Companion.final_prince
import com.example.influxbeta.view.activity.MainActivity.Companion.currencyType
import com.example.influxbeta.view.activity.MainActivity.Companion.foodcartData
import com.example.influxbeta.R
import com.example.influxbeta.databinding.FoodItemBinding
import com.example.influxbeta.model.Fnblist
import com.example.influxbeta.model.FoodCart
import com.example.influxbeta.model.Subitem
import org.greenrobot.eventbus.EventBus


class FoodDataAdapter(private val items: List<Fnblist>) : RecyclerView.Adapter<MyViewHolder>() {
    lateinit var binding: FoodItemBinding

    companion object {
        var final_prince = 0.0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.food_item, parent, false
        )
        return MyViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(items[position])

    }

}

class MyViewHolder(val binding: FoodItemBinding) : RecyclerView.ViewHolder(binding.root) {

    var quantirycount =0
    var price_amount =0.0
    var size_selected = false
    fun bind(item: Fnblist)
    {

        binding.apply {
            RadioUI(radioButton1, radioButton2, radioButton3)
            if (item.subItemCount > 1)
            {

                for (i in item.subitems.indices) {
                    var name = item.subitems[i].name.replace(" ", "")
                    when (name) {
                        "SMALL" -> radioButton1.visibility = View.VISIBLE
                        "MEDIUM" -> radioButton2.visibility = View.VISIBLE
                        "LARGE" -> radioButton3.visibility = View.VISIBLE
                    }
                }

                radioGroup.setOnCheckedChangeListener { group, checkedId ->
                    when (checkedId) {
                        R.id.radioButton1 -> {
                            radioButton1.isChecked = true
                            size_selected = true
                            price_amount = getThePrice("SMALL", item.subitems)!!.toDouble()

                        }
                        R.id.radioButton2 -> {
                            radioButton2.isChecked = true
                            size_selected = true
                            price_amount = getThePrice("MEDIUM", item.subitems)!!.toDouble()
                        }
                        R.id.radioButton3 -> {
                            radioButton3.isChecked = true
                            size_selected = true
                            price_amount = getThePrice("LARGE", item.subitems)!!.toDouble()
                        }
                    }

                    binding.price.text = currencyType + " " + price_amount
                }


            } else if (item.subItemCount == 1) {
                RadioUI(radioButton1, radioButton2, radioButton3)
                size_selected = true
                price_amount = getThePrice(item.subitems[0].name, item.subitems)!!.toDouble()
                binding.price.text = currencyType + " " + price_amount

            } else if (item.subItemCount == 0) {
                RadioUI(radioButton1, radioButton2, radioButton3)
                size_selected = true
                price_amount = item.itemPrice.toDouble()
                binding.price.text = currencyType + " " + price_amount

            } else {

            }

            Glide
                .with(binding.foodImageView.context)
                .load(item.imageUrl)
                .centerCrop()
                .into(binding.foodImageView);

            foodItemName.text = item.name

            minusPic.setOnClickListener {
                if(size_selected)
                {
                    if (quantirycount > 0) {
                        quantirycount = quantirycount-1
                        quantity.text = quantirycount.toString()
                        updatepricequantity()
                        updateCart(item.vistaFoodItemId.toInt(),item.name,quantirycount,price_amount.toDouble())
                    }
                }
                else
                {
                    Toast.makeText(binding.minusPic.context, "Select the Size of item", Toast.LENGTH_LONG).show()
                }

            }

            plusPic.setOnClickListener {
                if(size_selected) {
                    quantirycount = quantirycount + 1
                    quantity.text = quantirycount.toString()
                    updatepricequantity()
                    updateCart(
                        item.vistaFoodItemId.toInt(),
                        item.name,
                        quantirycount,
                        price_amount.toDouble()
                    )
                }
                else
                {
                    Toast.makeText(binding.minusPic.context, "Select the Size of item", Toast.LENGTH_LONG).show()
                }
            }



        }

    }

    fun RadioUI(radioButton1: RadioButton, radioButton2: RadioButton, radioButton3: RadioButton) {
        radioButton1.visibility = View.GONE
        radioButton2.visibility = View.GONE
        radioButton3.visibility = View.GONE
    }

    private fun updatepricequantity() {
        final_prince = quantirycount * price_amount
    }

    private fun updateCart(id:Int,name:String,quantity:Int,price:Double) {
        val foodCart = FoodCart(id,name,quantity,price)
        if(quantity<1)
        {
            foodcartData.remove(id)
        }
        else
        {
            foodcartData.put(id,foodCart)
        }

        EventBus.getDefault().post(foodcartData)

    }


    private fun getThePrice(
        size: String,
        subitems: List<Subitem>
    ): String? {

        var price: String = ""
        for (i in subitems.indices) {
            var _size = subitems[i].name.replace(" ", "")
            var size_temp = size.replace(" ", "")
            if (size_temp == _size) {
                price = subitems[i].subitemPrice.toString()
            }
        }
        return price
    }
}

