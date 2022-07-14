package com.ayizor.afeme.model.response

import com.ayizor.afeme.model.Region

data class RegionsResponse(
    val status: Boolean? = null,
    val message: String? = "",
    val data: ArrayList<Region>? = null
)