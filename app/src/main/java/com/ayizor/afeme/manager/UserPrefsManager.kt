package com.ayizor.afeme.manager

import android.content.Context
import android.content.SharedPreferences

class UserPrefsManager(context: Context) {

    val userSharedPreferences: SharedPreferences?


    init {
        userSharedPreferences = context.getSharedPreferences("user_db", Context.MODE_PRIVATE)

    }
    fun clearUserDatas() {
        userSharedPreferences?.edit()?.clear()?.commit()
    }


    fun storeUserId(id: String?) {
        val prefsEditor = userSharedPreferences!!.edit()
        prefsEditor.putString("user_id", id)
        prefsEditor.apply()
    }

    fun loadUserId(): String? {
        return userSharedPreferences!!.getString("user_id", "")
    }
    fun storeUserFirstname(id: String?) {
        val prefsEditor = userSharedPreferences!!.edit()
        prefsEditor.putString("user_firstname", id)
        prefsEditor.apply()
    }

    fun loadUserFirstname(): String? {
        return userSharedPreferences!!.getString("user_firstname", "")
    }
    fun storeUserLastname(id: String?) {
        val prefsEditor = userSharedPreferences!!.edit()
        prefsEditor.putString("user_lastname", id)
        prefsEditor.apply()
    }

    fun loadUserLastname(): String? {
        return userSharedPreferences!!.getString("user_lastname", "")
    }
    fun storeUserProfileType(profile_type: String?) {
        val prefsEditor = userSharedPreferences!!.edit()
        prefsEditor.putString("user_profile_type", profile_type)
        prefsEditor.apply()
    }

    fun loadUserProfileType(): String? {
        return userSharedPreferences!!.getString("user_profile_type", "")
    }

}