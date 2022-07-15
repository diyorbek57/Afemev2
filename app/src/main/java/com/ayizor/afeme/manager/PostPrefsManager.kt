package com.ayizor.afeme.manager

import android.content.Context
import android.content.SharedPreferences
import com.ayizor.afeme.model.inmodels.Area
import com.ayizor.afeme.model.inmodels.Floor
import com.ayizor.afeme.model.inmodels.Image
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class PostPrefsManager(context: Context) {
    val postSharedPreferences: SharedPreferences?

    init {

        postSharedPreferences = context.getSharedPreferences("post_db", Context.MODE_PRIVATE)
    }

    fun clearSavedPostDatas() {
        postSharedPreferences?.edit()?.clear()?.commit()
    }

    ///////////
    fun storePostType(id: Int) {
        val prefsEditor = postSharedPreferences!!.edit()
        prefsEditor.putInt("post_type", id)
        prefsEditor.apply()
    }

    fun loadPostType(): Int {
        return postSharedPreferences!!.getInt("post_type", 0)
    }

    ////////
    fun storeBuildingType(id: Int) {
        val prefsEditor = postSharedPreferences!!.edit()
        prefsEditor.putInt("building_type", id)
        prefsEditor.apply()
    }

    fun loadBuildingType(): Int {
        return postSharedPreferences!!.getInt("building_type", 0)
    }

    ///////
    fun storeLongitude(longitude: String) {
        val prefsEditor = postSharedPreferences!!.edit()
        prefsEditor.putString("longitude", longitude)
        prefsEditor.apply()
    }

    fun loadLongitude(): String? {
        return postSharedPreferences!!.getString("longitude", "")
    }

    ///////
    fun storeLatitude(latitude: String) {
        val prefsEditor = postSharedPreferences!!.edit()
        prefsEditor.putString("latitude", latitude)
        prefsEditor.apply()
    }

    fun loadLatitude(): String? {
        return postSharedPreferences!!.getString("latitude", "")
    }

    ///////
    fun storeRooms(rooms: String) {
        val prefsEditor = postSharedPreferences!!.edit()
        prefsEditor.putString("rooms", rooms)
        prefsEditor.apply()
    }

    fun loadRooms(): String? {
        return postSharedPreferences!!.getString("rooms", "")
    }

    ///////
    fun storeArea(area: Area) {
        val gson = Gson()
        val json = gson.toJson(area)
        val prefsEditor = postSharedPreferences!!.edit()
        prefsEditor.putString("area", json)
        prefsEditor.apply()
    }

    fun loadArea(): Area {
        val gson = Gson()
        val json: String? = postSharedPreferences?.getString("area", null)
        val type = object : TypeToken<Area>() {}.type
        return gson.fromJson(json, type)
    }

    ///////
    fun storeFloor(area: Floor) {
        val gson = Gson()
        val json = gson.toJson(area)
        val prefsEditor = postSharedPreferences!!.edit()
        prefsEditor.putString("floor", json)
        prefsEditor.apply()
    }

    fun loadFloor(): Floor {
        val gson = Gson()
        val json: String? = postSharedPreferences?.getString("floor", null)
        val type = object : TypeToken<Floor>() {}.type
        return gson.fromJson(json, type)
    }

    ///////
    fun storeImages(area: ArrayList<Image>) {
        val gson = Gson()
        val json = gson.toJson(area)
        val prefsEditor = postSharedPreferences!!.edit()
        prefsEditor.putString("images", json)
        prefsEditor.apply()
    }

    fun loadImages(): ArrayList<Image>? {
        val gson = Gson()
        val json: String? = postSharedPreferences?.getString("images", null)
        val type = object : TypeToken<ArrayList<Image?>?>() {}.type
        return try {
            gson.fromJson(json, type) as ArrayList<Image>
        } catch (e: NullPointerException) {
            null
        }


    }


    ///////
    fun storePrice(price: String) {
        val prefsEditor = postSharedPreferences!!.edit()
        prefsEditor.putString("price", price)
        prefsEditor.apply()
    }

    fun loadPrice(): String? {
        return postSharedPreferences!!.getString("price", "")
    }

    ///////
    fun storeDescription(description: String) {
        val prefsEditor = postSharedPreferences!!.edit()
        prefsEditor.putString("description", description)
        prefsEditor.apply()
    }

    fun loadDescription(): String? {
        return postSharedPreferences!!.getString("description", "")
    }
}