package com.example.influxbeta.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.influxbeta.model.FoodData
import com.example.influxbeta.repo.MainRepo
import com.example.influxbeta.utils.NetworkHelper
import com.example.influxbeta.utils.Resource
import kotlinx.coroutines.launch

class MainViewModel(
    private val mainRepo: MainRepo,
    private val networkHelper: NetworkHelper
):ViewModel()
{
    private val _foodData = MutableLiveData<Resource<FoodData>>()
    val foodData: LiveData<Resource<FoodData>>
    get() = _foodData

    init {
        fetchFoodData()
    }

    private fun fetchFoodData() {
        //add library for viewmodel scope.
        viewModelScope.launch {
            _foodData.postValue(Resource.loading(null))
            if(networkHelper.isNetworkConnected())
            {
                mainRepo.getData().let {
                    if(it.isSuccessful)
                    {
                       _foodData.postValue(Resource.success(it.body()))
                    }
                    else
                    {
                        _foodData.postValue(Resource.error(it.errorBody().toString(),null))
                    }
                }
            }
            else _foodData.postValue(Resource.error("No internet connection", null))
        }
    }
}