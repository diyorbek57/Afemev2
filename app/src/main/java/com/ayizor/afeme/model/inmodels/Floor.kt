package com.ayizor.afeme.model.inmodels

import com.google.gson.annotations.SerializedName

data class Floor(
    @SerializedName("floor")
    val house_floor: String? = "",
    @SerializedName("flat")
    val apartment_floor: String? = "",

    )
