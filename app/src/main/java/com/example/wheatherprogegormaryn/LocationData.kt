package com.example.wheatherprogegormaryn

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Handler
import android.util.Log
import androidx.core.app.ActivityCompat
import java.io.IOException
import java.util.*


class LocationData : LocationDataInterface{
    var result: String = ""

    @SuppressLint("MissingPermission")
    override fun setNowLocation(context: Context, mainActivity: MainActivity){
        val locationManager = mainActivity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        var locationNow = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)!!
        MainActivity.townName = getAdreesTown(locationNow, context)
        MainActivity.flag = true
    }

    override fun getAdreesTown(location: Location, context: Context): String {
        val thread = Thread(
            Runnable {
                val geocoder = Geocoder(context, Locale.getDefault())
                try{
                    var list: List<Address> = geocoder. getFromLocation(location.getLatitude(), location.getLongitude(), 1)
                    if(list != null && list.size > 0){
                        var address : Address = list.get(0) as Address
                        result = "${address.getLocality()}"
                    }
                }catch(e: IOException){
                    Log.d("Egor", "Подключение к геокодеру не установлено: $e.message")
                }
            }).run()
        return result
    }
}
