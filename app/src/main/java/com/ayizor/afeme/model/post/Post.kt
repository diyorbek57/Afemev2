package com.ayizor.afeme.model.post

import com.ayizor.afeme.model.inmodels.Video
import com.google.gson.annotations.SerializedName

data class Post(
    @SerializedName("images")
    val post_images: ArrayList<String>? = null,
    @SerializedName("videos")
    val post_videos: ArrayList<Video>? = null,

    @SerializedName("htype_id")
    val post_building_type: String? = null,

    @SerializedName("sale_id")
    val post_type: String? = null,

    @SerializedName("latitude")
    val post_latitude: String? = "",

    @SerializedName("longitude")
    val post_longitude: String? = "",

    @SerializedName("price_som")
    val post_price_uzs: String? = null,

    @SerializedName("price_usd")
    val post_price_usd: String? = null,

    @SerializedName("total_area_type")
    val post_area_type: String? = null,

    @SerializedName("total_area")
    val post_total_area: String? = null,

    @SerializedName("kitchen_area")
    val post_kitchen_area: String? = null,

    @SerializedName("living_area")
    val post_living_area: String? = null,

    @SerializedName("date")
    val post_built_year: String? = "",

    @SerializedName("room")
    val post_rooms: String? = "",

    @SerializedName("repair_id")
    val post_repair: String? = "",

    @SerializedName("documents")
    val post_documents: ArrayList<String>? = null,

    @SerializedName("description")
    val post_description: String? = "",

    @SerializedName("material_id")
    val post_material: String? = null,

    @SerializedName("region_id")
    val post_region_id: String? = null,

    @SerializedName("city_id")
    val post_city_id: String? = null,

    @SerializedName("street")
    val post_street: String? = "",

    @SerializedName("house")
    val post_house_number: String? = "",

    @SerializedName("floor")
    val post_floor: String? = "",

    @SerializedName("flat")
    val post_flat: String? = "",

    @SerializedName("ipoteka")
    val post_isIpoteka: Boolean? = false,

    @SerializedName("trade")
    val post_isTradable: Boolean? = false,

    )
