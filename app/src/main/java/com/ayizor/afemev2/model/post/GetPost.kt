package com.ayizor.afeme.model.post

import com.ayizor.afeme.model.Category
import com.ayizor.afeme.model.User
import com.ayizor.afeme.model.inmodels.*
import com.google.gson.annotations.SerializedName

data class GetPost(
    @SerializedName("id")
    val post_id: Int? = null,
    @SerializedName("image")
    val post_images: ArrayList<Image>? = null,
    @SerializedName("user")
    val user: User? = null,
    @SerializedName("videos")
    val post_videos: ArrayList<Video>? = null,
    @SerializedName("htype_id")
    val post_building_type: Category? = null,
    @SerializedName("sale_id")
    val post_type: PostType? = null,
    @SerializedName("latitude")
    val post_latitude: String? = "",
    @SerializedName("longitude")
    val post_longitude: String? = "",
    @SerializedName("price_som")
    val post_price_uzs: String? = null,
    @SerializedName("price_usd")
    val post_price_usd: String? = null,
    @SerializedName("area")
    val post_area: Area? = null,
    @SerializedName("date")
    val post_built_year: String? = "",
    @SerializedName("room")
    val post_rooms: String? = "",
    @SerializedName("repair_id")
    val post_repair: Repair? = null,
    @SerializedName("documents")
    val post_documents: ArrayList<Image>? = null,
    @SerializedName("description")
    val post_description: String? = "",
    @SerializedName("material_id")
    val post_material: Material? = null,
    @SerializedName("region_id")
    val post_region_id: Region? = null,
    @SerializedName("city_id")
    val post_city_id: Region? = null,
    @SerializedName("street")
    val post_street: String? = "",
    @SerializedName("house")
    val post_house_number: String? = "",
    @SerializedName("floor")
    val post_floor: String? = "",
    @SerializedName("flat")
    val post_flat: String? = "",
    @SerializedName("like")
    val post_isLiked: Boolean? = null,
    @SerializedName("reting")
    val post_rating: String? = null,
    @SerializedName("fullreyting")
    val post_rates: ArrayList<Rating>? = null,
    @SerializedName("ipoteka")
    val post_isIpoteka: Boolean? = false,
    @SerializedName("trade")
    val post_isTradable: Boolean? = false,
    @SerializedName("view")
    val post_views: String? = "",
    @SerializedName("likecount")
    val post_like_count: Int? = null,
    @SerializedName("created_at")
    val post_created_at: String? = "",
    @SerializedName("updated_at")
    val post_updated_at: String? = ""
)
