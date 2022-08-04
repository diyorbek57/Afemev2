package com.ayizor.afeme.model.user

import com.ayizor.afeme.model.post.GetPost
import com.ayizor.afeme.model.post.Post
import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("code")
    val user_code: String? = "",
    @SerializedName("id")
    val user_id: Int? = null,
    @SerializedName("name")
    val user_name: String? = null,
    @SerializedName("lastname")
    val user_last_name: String? = "",
    @SerializedName("image")
    val user_photo: String? = "",
    @SerializedName("email")
    val user_email: String? = "",
    @SerializedName("phone")
    val user_phone_number: String? = "",
    @SerializedName("user_type")
    val user_type: String? = "",
    @SerializedName("favorites")
    val user_favorites: ArrayList<GetPost>? = null,
    @SerializedName("devays_id")
    val user_device_id: String? = "",
    @SerializedName("devays_type")
    val user_device_type: String? = "",
    @SerializedName("password")
    val user_password: String? = "",
    @SerializedName("description")
    val user_description: String? = "",
    @SerializedName("experience")
    val user_experience: String? = "",
    @SerializedName("created_at")
    val user_created_at: String? = "",
    @SerializedName("updated_at")
    val user_updated_at: String? = "",
)
