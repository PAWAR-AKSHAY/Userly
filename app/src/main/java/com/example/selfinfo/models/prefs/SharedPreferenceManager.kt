package com.example.selfinfo.models.prefs

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences


const val PREFS = "USER_PREFS"
const val USER_ID = "USER_ID"
const val ADMIN_EMAIL = "ADMIN_EMAIL"
//const val THEME = "THEME"


object SharedPreferenceManager {

    private fun getSharedPreferences(context : Context) : SharedPreferences{
        return context.getSharedPreferences(PREFS,Activity.MODE_PRIVATE)
    }

    fun setUserId(context: Context, id : Int){
        with(getSharedPreferences(context).edit()){
            putInt(USER_ID,id)
                .apply()
        }
    }

    fun getUserId(context: Context): Int{
        return getSharedPreferences(context)
            .getInt(USER_ID,0)
    }

    fun setUserEmail(context: Context, email : String){
        with(getSharedPreferences(context).edit()){
            putString(ADMIN_EMAIL,email)
                .apply()
        }
    }

    fun getUserEmail(context: Context) : String? {
        return getSharedPreferences(context)
            .getString(ADMIN_EMAIL,"")
    }

    fun clearSharedPreferences(context: Context){
//        with(getSharedPreferences(context).edit()){
//                this.clear()
//                this.apply()
//        }
        val editor = getSharedPreferences(context).edit()
        editor.clear()
        editor.apply()
    }

//    fun setUserTheme(context: Context, theme : String){
//        with(getSharedPreferences(context).edit()){
//            putString(THEME,theme)
//                .apply()
//        }
//    }
}