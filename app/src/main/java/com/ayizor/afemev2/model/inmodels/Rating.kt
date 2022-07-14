package com.ayizor.afeme.model.inmodels

import com.google.gson.annotations.SerializedName

data class Rating(
    @SerializedName("id")
    val rating_id: Int? = null,
    @SerializedName("author")
    val rating_author: String? = "",
    @SerializedName("reltor_id")
    val rating_post_author_id: String? = "",
    @SerializedName("post_id")
    val rating_post_id: String? = "",
    @SerializedName("reting")
    val rating_count: String? = "",
    @SerializedName("comment")
    val rating_comment: String? = "",
    @SerializedName("created_at")
    val rating_created_at: String? = "",
    @SerializedName("updated_at")
    val rating_updated_at: String? = ""
)
