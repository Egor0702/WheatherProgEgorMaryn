package com.example.wheatherprogegormaryn
import android.content.Context
import android.location.Location


interface LocationDataInterface {
    fun setNowLocation(context: Context, mainActivity: MainActivity)
    fun getAdreesTown(location : Location, context : Context): String
}