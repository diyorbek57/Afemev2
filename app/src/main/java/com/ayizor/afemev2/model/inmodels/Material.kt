package com.ayizor.afeme.model.inmodels

import com.google.gson.annotations.SerializedName

data class Material(
    @SerializedName("id")
    val material_id: Int? = null,
    @SerializedName("name_uz")
    val material_name_uz: String? = "",
    @SerializedName("name_ru")
    val material_name_ru: String? = "",
    @SerializedName("name_en")
    val material_name_en: String? = "",
    @SerializedName("created_at")
    val material_created_at: String? = "",
    @SerializedName("updated_at")
    val material_updated_at: String? = ""
)