package com.example.wheatherprogegormaryn

interface DataProcessingCallback {
    fun onSuccessfulDataProcessed(wheather:String, temp:Int)
    fun showToastText(string : String)
}