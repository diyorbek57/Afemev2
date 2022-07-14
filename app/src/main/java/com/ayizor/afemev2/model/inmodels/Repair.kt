package com.ayizor.afeme.model.inmodels

import com.google.gson.annotations.SerializedName

data class Repair(
    @SerializedName("id")
    val repair_id: Int? = null,
    @SerializedName("name_uz")
    val repair_name_uz: String? = "",
    @SerializedName("name_ru")
    val repair_name_ru: String? = "",
    @SerializedName("name_en")
    val repair_name_en: String? = "",
    @SerializedName("created_at")
    val repair_created_at: String? = "",
    @SerializedName("updated_at")
    val repair_updated_at: String? = ""
)
