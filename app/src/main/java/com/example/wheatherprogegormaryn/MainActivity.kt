package com.example.wheatherprogegormaryn

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout



class MainActivity : AppCompatActivity(), DataProcessingCallback {
    companion object{
        internal var townName : String = String()
        internal var flag : Boolean = false
    }
    private lateinit var nameTown : TextView
    private lateinit var infWheather:TextView
    private lateinit var autoComplete : AutoCompleteTextView
    private lateinit var button : Button
    private lateinit var image : ImageView
    private lateinit var swipeRefreshLayout : SwipeRefreshLayout
    private lateinit var dataProcessing : DataProcessing
    private lateinit var locationData: LocationDataInterface
    private val accessLocation: String = android.Manifest.permission.ACCESS_FINE_LOCATION
    private val REQUEST_CODE = 1
    private val TAG = "Egor"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState != null) {
            townName = savedInstanceState.getString("townName").toString()
            var byteValue = savedInstanceState.getByte("flag").toInt()
            if(byteValue == 0) flag = false
            else flag = true
        }

        nameTown = findViewById(R.id.name_town)
        infWheather = findViewById(R.id.inf_wheather)
        autoComplete = findViewById(R.id.auto_complete)
        button = findViewById(R.id.button_width)
        image = findViewById(R.id.image)
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)
        dataProcessing = DataProcessing()
        locationData = LocationData()

        //Log.d(TAG, "flag: $flag")

        try{
            if(ActivityCompat.checkSelfPermission(this, accessLocation) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(accessLocation), REQUEST_CODE)
            }else{
                Log.d(TAG, "мы в разрешении")
                if (!flag)
                    locationData.setNowLocation(this,this)
                showData()
            }
        }catch(e:Exception){
            Log.d("Egor", "Error permission: ${e.message}")}

        button.setOnClickListener{
            townName = autoComplete.getText().toString().trim()
            showData() }
        swipeRefreshLayout.setOnRefreshListener{
            showData()
            swipeRefreshLayout.isRefreshing = false
        }

        val arrayString = resources.getStringArray(R.array.name_town)
        val adapter = ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line, arrayString)
        autoComplete.setAdapter(adapter)
    }

    private fun showData(){
        Log.d(TAG, "townName in showData: $townName")
        if (!townName.equals("")) {
            dataProcessing.sendRequest(townName, this)
        } else
            Toast.makeText(this, "Введите название населенного пункта", Toast.LENGTH_SHORT).show()
    }


    override fun onSuccessfulDataProcessed(wheather:String, temp:Int) {
        Log.d(TAG, "Мы в onSuccses")
        var wheatherInfo = ""
        val tempWhheather = temp ?: 0
        when(wheather){
            "Clear" -> {image.setImageResource(R.drawable.clear)
                wheatherInfo = "Ясно"}
            "Clouds" -> {image.setImageResource(R.drawable.clouds)
                wheatherInfo = "Облачно"}
            "Rain" -> {image.setImageResource(R.drawable.rain)
                wheatherInfo = "Дождь"}
        }
        nameTown.text = townName
        if (tempWhheather >= 0)
            infWheather.text = "$wheatherInfo   +$tempWhheather"
        else
            infWheather.text = "$wheatherInfo   -$tempWhheather"
        //editText.text= " ".toString()
        //     Log.d(TAG, "flag: $flag")
    }
    override fun showToastText(stringText: String){
        Toast.makeText(this, stringText,Toast.LENGTH_LONG ).show()
    }

    override fun onRequestPermissionsResult(requestCode:Int, permissions : Array<String>, grantResults: IntArray){
        when(requestCode){
            REQUEST_CODE -> if(grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationData.setNowLocation(this, this)
                showData()
            }
            }
        }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("townName", townName)
        if(!flag) outState.putByte("flag", 0)
        else outState.putByte("flag", 1)
    }
}
