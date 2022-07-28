package com.ayizor.afeme.model.inmodels

import com.google.gson.annotations.SerializedName

data class BuildingMaterial(
    @SerializedName("id")
    val material_id: Int,
    @SerializedName("name_uz")
    val material_name_uz: String,
    @SerializedName("name_en")
    val material_name_en: String,
    @SerializedName("name_ru")
    val material_name_ru: String,
    @SerializedName("updated_at")
    val material_updated_at: String,
    @SerializedName("created_at")
    val material_created_at: String,
)