package com.example.youtubeplayer

import android.content.Context
import android.content.SharedPreferences

class MyPref(context:Context){
    private var fileName = "Last Url"
    private val pref:SharedPreferences = context.getSharedPreferences("My Preference",0)
    private val editor = pref.edit()

    fun savePref(url:String){
        editor.putString(fileName,url)
        editor.apply()
    }

    fun getPref() : String = pref.getString(fileName,"").toString()
}
