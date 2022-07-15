package com.ayizor.afeme.model

import com.google.gson.annotations.SerializedName

data class City(
    @SerializedName("id")
    val city_id: Int? = null,
    @SerializedName("name_uz")
    val city_name_uz: String? = "",
    @SerializedName("name_ru")
    val city_name_ru: String? = "",
    @SerializedName("name_en")
    val city_name_en: String? = "",
    @SerializedName("region_id")
    val city_region_id: String? = "",
    @SerializedName("created_at")
    val region_created_at: String? = "",
    @SerializedName("updated_at")
    val region_updated_at: String? = ""
)
