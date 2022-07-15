package com.ayizor.afeme.model.inmodels

import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("id")
    val image_id: Int? = null,
    @SerializedName("url")
    val image_url: String? = null,
    @SerializedName("post_id")
    val image_post_id: String? = null,
    @SerializedName("created_at")
    val image_created_at: String? = null,
    @SerializedName("updated_at")
    val image_updated_at: String? = null
)