package com.example.wheatherprogegormaryn

import android.util.Log
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DataProcessing {
    private val retrofitImpl: RetrofitImpl = RetrofitImpl()
    private val mainActivity = MainActivity()
    internal fun sendRequest(townName:String, instance : DataProcessingCallback){
        retrofitImpl.getRequest().showWeather(townName).enqueue(object : Callback<DateWeather> {
            override fun onResponse(call: retrofit2.Call<DateWeather>, response: Response<DateWeather>) {
                if (response.isSuccessful && response.body() != null) {
                    processingData(response.body(), null, instance)
                } else
                    processingData(null, Throwable("ответ не получен"), instance)
            }
            override fun onFailure(call: Call<DateWeather>, t: Throwable) {
                Log.d("Main", "onFailure")
                processingData(null, t, instance)
            }
        })
    }
    private fun processingData(dateWeather:DateWeather?, error: Throwable?, instance : DataProcessingCallback){
        if (dateWeather == null || error != null) {
            Log.d("Egor", "error: ${error!!.message.toString()}")
            instance.showToastText("Произошла ошибка \n Возможно вы неправильно ввели название населенного пункта")
        } else {
            if (dateWeather == null) Log.d("Main", "Loose")
            else {
                val string = dateWeather.weather.get(0).toString()
                val size = string.length - 1
                instance.onSuccessfulDataProcessed(string.subSequence(13, size).toString(), dateWeather.main.temp!!.toInt())
            }

        }
    }

}
