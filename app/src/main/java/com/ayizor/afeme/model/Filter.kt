package com.ayizor.afeme.model

import com.google.gson.annotations.SerializedName

data class Filter(
    @SerializedName("keyword")
    val filter_keyword: Int? = null,
    @SerializedName("room")
    val filter_room: String? = "",
    @SerializedName("htype_id")
    val filter_htype_id: String? = "",
    @SerializedName("city_id")
    val filter_city_id: String? = "",
    @SerializedName("region_id")
    val filter_region_id: String? = "",
    @SerializedName("perpage")
    val filter_perpage: String? = "",
)
