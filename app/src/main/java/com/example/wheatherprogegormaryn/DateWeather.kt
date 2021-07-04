package com.example.wheatherprogegormaryn

data class DateWeather(
    val main: Main,
    val weather : List<Weather>

)

data class Main(
    val temp : Double?
)
data class Weather(
    val main: String
)

