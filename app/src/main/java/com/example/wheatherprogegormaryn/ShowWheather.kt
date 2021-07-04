package com.example.wheatherprogegormaryn
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ShowWeather{
    @GET("weather?&appid=7bd809a46137d450c7abc5255a280afe&units=metric")
    fun showWeather(@Query("q") town: String): Call<DateWeather>
}
