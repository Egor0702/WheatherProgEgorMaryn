package com.example.wheatherprogegormaryn
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitImpl{
    fun getRequest() : ShowWeather{
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("http://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofitBuilder.create(ShowWeather::class.java)
    }
}