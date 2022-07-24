package com.ayizor.afeme.model.chat

import com.google.gson.annotations.SerializedName

data class Messages(
    @SerializedName("")
    val message_image: String? = "",
    @SerializedName("username")
    val message_username: String? = "",
    @SerializedName("username")
    val message_last_message: String? = "",
    @SerializedName("")
    val message_last_time: String? = "",

)
