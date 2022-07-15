package com.ayizor.afeme.model

import com.google.gson.annotations.SerializedName

data class Region(
    @SerializedName("id")
    val region_id: Int? = null,
    @SerializedName("name_uz")
    val region_name_uz: String? = "",
    @SerializedName("name_ru")
    val region_name_ru: String? = "",
    @SerializedName("name_en")
    val region_name_en: String? = "",
    @SerializedName("image")
    val region_image: String? = "",
    @SerializedName("citys")
    val region_citys: City? = null,
    @SerializedName("created_at")
    val region_created_at: String? = "",
    @SerializedName("updated_at")
    val region_updated_at: String? = ""
)
